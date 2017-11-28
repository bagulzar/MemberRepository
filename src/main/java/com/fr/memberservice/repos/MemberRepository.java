package com.fr.memberservice.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fr.memberservice.models.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {

	@Query("select id from Member")
	List<Integer> getAllIds();
}
