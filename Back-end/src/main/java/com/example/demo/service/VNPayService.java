package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputFilter.Config;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.EOrderStatus;
import com.example.demo.common.EPaymentStatus;
import com.example.demo.common.VNPayUtils;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.Product_SKU;
import com.example.demo.payload.VNPayPaymentRequest;
import com.example.demo.payload.VNPayPaymentResponse;
import com.example.demo.payload.VNPayRefundRequest;
import com.example.demo.payload.VNPayRefundResponse;
import com.example.demo.payload.VnPayQueryRequest;
import com.example.demo.payload.VnPayQueryResponse;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSKURepository;

@Service
@Transactional
public class VNPayService {
	@Autowired
	private VNPayUtils vnPayUtils;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductSKURepository productSKURepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	CartRepository cartRepository;

	public VnPayQueryResponse vnpQuery (VnPayQueryRequest vnpRequest, HttpServletRequest req) throws IOException {
		String vnp_TxnRef = vnpRequest.getVnpOrderId();
        String vnp_TransDate = vnpRequest.getVnpTransDate();
        String vnp_TmnCode = vnPayUtils.vnp_TmnCode;
        String vnp_IpAddr = vnPayUtils.getIpAddress(req);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "querydr");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
        vnp_Params.put("vnp_TransDate", vnp_TransDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayUtils.hmacSHA512(vnPayUtils.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayUtils.vnp_apiUrl + "?" + queryUrl;
        URL url = new URL(paymentUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String Rsp = response.toString();
        String respDecode = URLDecoder.decode(Rsp, "UTF-8");
        String[] responseData = respDecode.split("&|\\=");
        VnPayQueryResponse vnpResponse = new VnPayQueryResponse();
        vnpResponse.setData(Arrays.toString(responseData));
        return vnpResponse;
	}

	public VNPayRefundResponse vnpRefund (VNPayRefundRequest vnpRefund, HttpServletRequest req) throws IOException {
		//vnp_Command = refund
        String vnp_TxnRef = vnpRefund.getVnpOrderId();
        String vnp_TransDate = vnpRefund.getTransDate();
        String email = vnpRefund.getEmail();
        int amount = vnpRefund.getAmount()*100;
        String trantype = vnpRefund.getTrantype();
        String vnp_TmnCode = vnPayUtils.vnp_TmnCode;
        String vnp_IpAddr = vnPayUtils.getIpAddress(req);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "refund");
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
        vnp_Params.put("vnp_TransDate", vnp_TransDate);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateBy", email);
        vnp_Params.put("vnp_TransactionType", trantype);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayUtils.hmacSHA512(vnPayUtils.vnp_HashSecret , hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayUtils.vnp_apiUrl + "?" + queryUrl;
        URL url = new URL(paymentUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String Rsp = response.toString();
        String respDecode = URLDecoder.decode(Rsp, "UTF-8");
        String[] responseData = respDecode.split("&|\\=");
        VNPayRefundResponse vnpResponse = new VNPayRefundResponse();
        vnpResponse.setData(Arrays.toString(responseData));
        return vnpResponse;
	}

	public VNPayPaymentResponse vnpCreatePayment (VNPayPaymentRequest vnpPayment, HttpServletRequest req) throws IOException {

		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = vnpPayment.getVnpOrderInfo();
        String orderType = vnPayUtils.orderType;
        String vnp_TxnRef = vnPayUtils.getRandomNumber(8);
        String vnp_IpAddr = vnPayUtils.getIpAddress(req);
        String vnp_TmnCode = vnPayUtils.vnp_TmnCode;

        int amount = vnpPayment.getAmount() * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = vnpPayment.getBankCode();
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = vnpPayment.getLanguage();
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", req.getRequestURL().toString() + VNPayUtils.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
        vnp_Params.put("vnp_Bill_Mobile", vnpPayment.getBillingMoblie());
        vnp_Params.put("vnp_Bill_Email", vnpPayment.getBillingEmail());
        String fullName = vnpPayment.getBillingFullname().trim();
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);

        }
        vnp_Params.put("vnp_Bill_Address", vnpPayment.getBillingAddress());
        vnp_Params.put("vnp_Bill_City", vnpPayment.getBillingCity());
        vnp_Params.put("vnp_Bill_Country", vnpPayment.getBillingCountry());
        if (vnpPayment.getBillingState() != null && !vnpPayment.getBillingState().isEmpty()) {
            vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
        }
        // Invoice
        vnp_Params.put("vnp_Inv_Phone", vnpPayment.getInvMoblie());
        vnp_Params.put("vnp_Inv_Email", vnpPayment.getBillingEmail());
        vnp_Params.put("vnp_Inv_Customer", vnpPayment.getInvCustomer());
        vnp_Params.put("vnp_Inv_Address", vnpPayment.getInvAddress());
        vnp_Params.put("vnp_Inv_Company", vnpPayment.getInvCompany());
        vnp_Params.put("vnp_Inv_Taxcode", vnpPayment.getInvTaxcode());
        vnp_Params.put("vnp_Inv_Type", vnpPayment.getInvType());
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayUtils.hmacSHA512(vnPayUtils.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayUtils.vnp_PayUrl + "?" + queryUrl;
        VNPayPaymentResponse vnpResponse = new VNPayPaymentResponse();
        vnpResponse.setCode("00");
        vnpResponse.setMessage("success");
        vnpResponse.setData(paymentUrl);
        return vnpResponse;
	}

	public String checkResult (HttpServletRequest request) {
		Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = vnPayUtils.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                Order order = orderRepository.findById(Long.valueOf(request.getParameter("vnp_OrderInfo"))).get();
                order.setPaymentStatus(EPaymentStatus.COMPLETED);
                order.setOrderStatus(EOrderStatus.PROCCESSING);
                order.setPaymentDate(Calendar.getInstance().getTime());
                orderRepository.saveAndFlush(order);
                Cart cart = cartRepository.findByUser(order.getUser()).get();
    			cart.setCartItems(null);
    			cartRepository.saveAndFlush(cart);
                Set<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem orderItem : orderItems) {
                	Product product = orderItem.getProductSKU().getProducts();
    				product.setSold(product.getSold() + orderItem.getQuantity());
    				productRepository.save(product);
                }

                return "Thanh toan thanh cong don hang " + request.getParameter("vnp_OrderInfo") + ". Ma don hang: " + request.getParameter("vnp_TxnRef") + ". So tien: " + request.getParameter("vnp_Amount");
            } else {
            	Order order = orderRepository.findById(Long.valueOf(request.getParameter("vnp_OrderInfo"))).get();
                order.setPaymentStatus(EPaymentStatus.FAILED);
                order.setOrderStatus(EOrderStatus.CANCELLED);
                orderRepository.saveAndFlush(order);
                Set<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem orderItem : orderItems) {
                	Product_SKU productSKU = orderItem.getProductSKU();
                	productSKU.setStock(productSKU.getStock() + orderItem.getQuantity());
                }
                return "Error: Thanh toan khong thanh cong don hang " + request.getParameter("vnp_OrderInfo");
            }

        } else {
            return "Error: Chu ky khong hop le";
        }
	}
}
