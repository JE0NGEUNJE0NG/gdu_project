package com.cu.gdu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cu.gdu.dto.AffReservationDto;
import com.cu.gdu.dto.AffiliatedOrganDto;
import com.cu.gdu.dto.AttachDto;
import com.cu.gdu.dto.MajorDto;
import com.cu.gdu.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AffiliatedOrganDao {

	public static final int selectAffiliatedOrganResListCount = 0;
	private final SqlSessionTemplate sqlSessionTemplate;
	
	// * ------------------- 부속기관 목록관련 -------------------
	public int selectAffiliatedOrganListCount() {
		return sqlSessionTemplate.selectOne("affiliatedOrganMapper.selectAffiliatedOrganListCount");
	}
	
	public List<AffiliatedOrganDto> selectAffiliatedOrganList(PageInfoDto pi){
		
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage() - 1) * limit;
		
		// RowBounds rowBounds = new RowBounds(몇개의게시글건너뛰고, 몇개조회할건지);
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return sqlSessionTemplate.selectList("affiliatedOrganMapper.selectAffiliatedOrganList", null, rowBounds);
	}
	// * ------------------- 부속기관 예약관련 -------------------
	public AffiliatedOrganDto selectAffiliatedOrgan(int affNo) {
		return sqlSessionTemplate.selectOne("affiliatedOrganMapper.selectAffiliatedOrgan", affNo);
	}
	
	public int insertAffiliatedOrganRes(AffReservationDto affres) {
		return sqlSessionTemplate.insert("affiliatedOrganMapper.insertAffiliatedOrganRes", affres);
	}
	
	public int selectAffiliatedOrganResListCount() {
		return sqlSessionTemplate.selectOne("affiliatedOrganMapper.selectAffiliatedOrganResListCount");
	}

	public List<AffReservationDto> selectAffiliatedOrganResList(){
		return sqlSessionTemplate.selectList("affiliatedOrganMapper.selectAffiliatedOrganResList");
	}
	// * ------------------- 부속기관 등록관련 -------------------
	public int insertAffiliatedOrgan(AffiliatedOrganDto aff) {
		return sqlSessionTemplate.insert("affiliatedOrganMapper.insertAffiliatedOrgan", aff);
	}
	
	public int insertAttach(AttachDto at) {
		return sqlSessionTemplate.insert("affiliatedOrganMapper.insertAttach", at);
	}
	
}
