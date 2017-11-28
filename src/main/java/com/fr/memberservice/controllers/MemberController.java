package com.fr.memberservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fr.memberservice.models.Member;
import com.fr.memberservice.repos.MemberRepository;
import com.fr.memberservice.util.CommonUtils;
import com.fr.memberservice.util.ValidationUtil;
import com.google.gson.Gson;

@RestController
public class MemberController {

	@Autowired
	private MemberRepository memberRepository;

	@RequestMapping(method = RequestMethod.POST, path = "/member/", produces = "application/json")
	public ResponseEntity<?> addMember(@RequestBody Member member, UriComponentsBuilder uriComponentsBuilder) {
		Gson gson = new Gson();
		HttpHeaders headers = new HttpHeaders();
		Map<String, String> validationErros = new HashMap<String, String>();

		try {
			member.setId(null);

			validationErros = ValidationUtil.validateMemberRequest(member);
			if (validationErros.isEmpty()) {
				memberRepository.save(member);
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setLocation(uriComponentsBuilder.path("/member/{memberId}").buildAndExpand(member.getId()).toUri());
				return new ResponseEntity<String>(gson.toJson("Created new member with Id: " + member.getId()), headers, HttpStatus.CREATED);

			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			headers.setContentType(MediaType.APPLICATION_JSON);
			return new ResponseEntity<String>(gson.toJson(validationErros), headers, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/member", produces = "application/json")
	public ResponseEntity<?> getMembers() {
		List<Integer> memberIdsList = null;
		Gson gson = new Gson();

		try {
			memberIdsList = (List<Integer>) memberRepository.getAllIds();

			if (CommonUtils.isNullOrEmpty(memberIdsList)) {
				return new ResponseEntity<String>(gson.toJson("No member exist"), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<List<Integer>>(memberIdsList, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(gson.toJson("Member could not be found"), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/member/{memberId}", produces = "application/json")
	public ResponseEntity<?> getMemberById(@PathVariable("memberId") String id) {
		Member member = null;
		Gson gson = new Gson();

		try {
			member = memberRepository.findOne(Integer.parseInt(id));

			if (member != null) {
				return new ResponseEntity<Member>(member, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(gson.toJson("Member could not be found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(gson.toJson("Bad Request"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/member/{memberId}", produces = "application/json")
	public ResponseEntity<?> updatePizza(@RequestBody Member requestMember, @PathVariable("memberId") String memberId) {
		Member member = null;
		Gson gson = new Gson();

		try {
			member = memberRepository.findOne(Integer.parseInt(memberId));

			if (member != null) {

				if (!CommonUtils.isNullOrEmpty(requestMember.getFirstName())) {
					member.setFirstName(requestMember.getFirstName());
				}

				if (!CommonUtils.isNullOrEmpty(requestMember.getLastName())) {
					member.setLastName(requestMember.getLastName());
				}

				if (!CommonUtils.isNullOrEmpty(requestMember.getDob())) {
					member.setDob(requestMember.getDob());
				}

				if (!CommonUtils.isNullOrEmpty(requestMember.getPostalCode())) {
					member.setPostalCode(requestMember.getPostalCode());
				}

				memberRepository.save(member);

				return new ResponseEntity<Member>(member, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<String>(gson.toJson("Member could not be found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(gson.toJson("Bad Request"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/member/{memberId}", produces = "application/json")
	public ResponseEntity<?> deletePizza(@PathVariable("memberId") String memberId) {
		Member member = null;
		Gson gson = new Gson();

		try {
			member = memberRepository.findOne(Integer.parseInt(memberId));

			if (member != null) {
				memberRepository.delete(member);
				return new ResponseEntity<Member>(member, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<String>(gson.toJson("Member could not be found"), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(gson.toJson("Bad Request"), HttpStatus.BAD_REQUEST);
		}
	}

}
