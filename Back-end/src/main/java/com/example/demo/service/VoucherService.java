package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.VoucherRepository;
import com.example.demo.entity.Voucher;
import com.example.demo.payload.CartTotalResponse;
import com.example.demo.payload.VoucherDto;
import com.example.demo.common.EVoucherType;

@Service
@Transactional
public class VoucherService {

	@Autowired
	VoucherRepository voucherRepository;

	public List<VoucherDto> getVoucherList() {
		List<Voucher> voucherList = voucherRepository.findAll();
		if(voucherList.isEmpty())
			return new ArrayList<>();
		List<VoucherDto> voucherDtoList = new ArrayList<>();
		VoucherDto voucherDto = new VoucherDto();
		for (Voucher voucher : voucherList) {
			voucherDto.setId(voucher.getId());
			voucherDto.setCode(voucher.getCode());
			voucherDto.setName(voucher.getName());
			voucherDto.setDescription(voucher.getDescription());
			voucherDto.setType(voucher.getType().toString());
			voucherDto.setMinSpend(voucher.getMinSpend());
			voucherDto.setMaxDiscount(voucher.getMaxDiscount());
			voucherDto.setDiscountAmount(voucher.getDiscountAmount());
			voucherDto.setActive(voucher.isActive());

			voucherDtoList.add(voucherDto);
		}
		return voucherDtoList;
	}

	public VoucherDto getVoucherDto(Voucher voucher) {
		VoucherDto voucherDto = new VoucherDto();

		voucherDto.setId(voucher.getId());
		voucherDto.setCode(voucher.getCode());
		voucherDto.setName(voucher.getName());
		voucherDto.setDescription(voucher.getDescription());
		voucherDto.setType(voucher.getType().toString());
		voucherDto.setMinSpend(voucher.getMinSpend());
		voucherDto.setMaxDiscount(voucher.getMaxDiscount());
		voucherDto.setDiscountAmount(voucher.getDiscountAmount());
		voucherDto.setActive(voucher.isActive());

		return voucherDto;
	}

	public VoucherDto getVoucherByCode(String code) {
		Voucher voucher = voucherRepository.findByCode(code).get();
		if (voucher == null)
			return null;
		VoucherDto voucherDto = new VoucherDto();

		voucherDto.setId(voucher.getId());
		voucherDto.setCode(voucher.getCode());
		voucherDto.setName(voucher.getName());
		voucherDto.setDescription(voucher.getDescription());
		voucherDto.setType(voucher.getType().toString());
		voucherDto.setMinSpend(voucher.getMinSpend());
		voucherDto.setMaxDiscount(voucher.getMaxDiscount());
		voucherDto.setDiscountAmount(voucher.getDiscountAmount());
		voucherDto.setActive(voucher.isActive());

		return voucherDto;
	}

	public VoucherDto getVoucherById(Long id) {
		Voucher voucher = voucherRepository.findById(id).get();
		if (voucher == null)
			return null;
		VoucherDto voucherDto = new VoucherDto();

		voucherDto.setId(voucher.getId());
		voucherDto.setCode(voucher.getCode());
		voucherDto.setName(voucher.getName());
		voucherDto.setDescription(voucher.getDescription());
		voucherDto.setType(voucher.getType().toString());
		voucherDto.setMinSpend(voucher.getMinSpend());
		voucherDto.setMaxDiscount(voucher.getMaxDiscount());
		voucherDto.setDiscountAmount(voucher.getDiscountAmount());
		voucherDto.setActive(voucher.isActive());

		return voucherDto;
	}

	public VoucherDto createVoucher(VoucherDto voucherDto) {
		if (getVoucherByCode(voucherDto.getCode()) != null)
			return null;
		Voucher voucher = new Voucher();

		voucher.setCode(voucherDto.getCode());
		voucher.setName(voucherDto.getName());
		voucher.setDescription(voucherDto.getDescription());
		switch (voucherDto.getType()) {
		case "FIX_VALUE":
			voucher.setType(EVoucherType.FIX_VALUE);
			break;
		case "PERCENTAGE":
			voucher.setType(EVoucherType.PERCENTAGE);
			break;
		}
		voucher.setMinSpend(voucherDto.getMinSpend());
		voucher.setMaxDiscount(voucherDto.getMaxDiscount());
		voucher.setDiscountAmount(voucherDto.getDiscountAmount());
		voucher.setActive(voucherDto.isActive());
		voucherRepository.saveAndFlush(voucher);

		return getVoucherDto(voucher);

	}

	public VoucherDto changeVoucherActivation (Long id) {
		Voucher voucher = voucherRepository.findById(id).get();
		if (voucher == null)
			return null;

		if (voucher.isActive())
			voucher.setActive(false);
		else
			voucher.setActive(true);
		voucherRepository.save(voucher);

		VoucherDto voucherDto = new VoucherDto();

		voucherDto.setId(voucher.getId());
		voucherDto.setCode(voucher.getCode());
		voucherDto.setName(voucher.getName());
		voucherDto.setDescription(voucher.getDescription());
		voucherDto.setType(voucher.getType().toString());
		voucherDto.setMinSpend(voucher.getMinSpend());
		voucherDto.setMaxDiscount(voucher.getMaxDiscount());
		voucherDto.setDiscountAmount(voucher.getDiscountAmount());
		voucherDto.setActive(voucher.isActive());

		return voucherDto;
	}

	public VoucherDto updateVoucher(Long id, VoucherDto voucherDto) {
		Voucher voucher = voucherRepository.findById(id).get();
		if (voucher == null)
			return null;

		voucher.setName(voucherDto.getName());
		voucher.setDescription(voucherDto.getDescription());
		switch (voucherDto.getType()) {
		case "FIX_VALUE":
			voucher.setType(EVoucherType.FIX_VALUE);
			break;
		case "PERCENTAGE":
			voucher.setType(EVoucherType.PERCENTAGE);
			break;
		}
		voucher.setMinSpend(voucherDto.getMinSpend());
		voucher.setMaxDiscount(voucherDto.getMaxDiscount());
		voucher.setDiscountAmount(voucherDto.getDiscountAmount());
		voucher.setActive(voucherDto.isActive());
		voucherRepository.save(voucher);

		return getVoucherDto(voucher);
	}

	public void deleteVoucher(Long id) {
		voucherRepository.deleteById(id);
	}

	public int validateVoucher (String code, Double cartTotal) {
		Voucher voucher = voucherRepository.findByCode(code).get();
		if (voucher == null)
			return 0;
		else if (!voucher.isActive())
			return 1;
		else if (cartTotal > voucher.getMinSpend())
			return 2;
		else
			return 3;
	}

	public CartTotalResponse calculateDiscount(String code, Double cartTotal) {

		Voucher voucher = voucherRepository.findByCode(code).get();

		CartTotalResponse newCartTotal = new CartTotalResponse();
		newCartTotal.setCode(code);
		if (voucher.getType().equals(EVoucherType.FIX_VALUE)) {
			newCartTotal.setDiscountValue(voucher.getDiscountAmount());
			newCartTotal.setNewTotal(cartTotal-newCartTotal.getDiscountValue());
		} else if (voucher.getType().equals(EVoucherType.PERCENTAGE)) {
			newCartTotal.setDiscountValue((cartTotal*voucher.getDiscountAmount())/100);
			newCartTotal.setNewTotal(cartTotal-newCartTotal.getDiscountValue());
		}
		return newCartTotal;
	}



}
