package com.cu.gdu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cu.gdu.dao.CalendarDao;
import com.cu.gdu.dto.CalCtgDto;
import com.cu.gdu.dto.CalendarDto;
import com.cu.gdu.dto.ShareMemDto;
import com.cu.gdu.dto.ShareMemDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalendarServiceImpl implements CalendarService {
	
	private final CalendarDao calendarDao;

	// 김영주 -------------------------------------------------------

	/**
	 * 캘린더(카테고리) 조회
	 * @author 김영주
	 */
	@Override
	public List<CalCtgDto> selectCalCtgList(int memNo) {
		return calendarDao.selectCalCtgList(memNo);
	}
	
	/**
	 * 캘린더(카테고리) 등록
	 * @author 김영주
	 */
	@Override
	public int insertCalCtg(CalCtgDto ctg) {
		
		// 캘린더(카테고리) insert
		int result1 = calendarDao.insertCalCtg(ctg);
		
		int result2 = 1;
		// 공유직원 insert
		List<ShareMemDto> sList = ctg.getShList();
		if(!sList.isEmpty()) {
			result2 = 0;
			for(ShareMemDto sh : sList) {
				result2 += calendarDao.insertShareMem(sh);
			}
		}
		
		
		return result1 * result2;
	}

	/**
	 * 캘린더(카테고리) 수정
	 * @author 김영주
	 */
	@Override
	public int updateCalCtg(CalCtgDto ctg) {
		return calendarDao.updateCalCtg(ctg);
	}
	
	/**
	 * 캘린더(카테고리) 삭제
	 * @author 김영주
	 */
	@Override
	public int deleteCalCtg(int ctgNo) {
		return calendarDao.deleteCalCtg(ctgNo);
	}
	
	
	/**
	 * 일정 등록
	 * @author 김영주
	 */
	@Override
	public int insertCalendar(CalendarDto cal) {
		return calendarDao.insertCalendar(cal);
	}

	/**
	 * 일정 조회
	 * @author 김영주
	 */
	@Override
	public List<CalendarDto> selectCalendarList(String[] showList) {
		return calendarDao.selectCalendarList(showList);
	}

	/**
	 * 일정 수정
	 * @author 김영주
	 */
	@Override
	public int updateCalendar(CalendarDto cal) {
		return calendarDao.updateCalendar(cal);
	}

	/**
	 * 일정 삭제
	 * @author 김영주
	 */
	@Override
	public int deleteCalendar(int delType, int delNo) {
		
		Map<String, Integer> delInfo = new HashMap<>();
		delInfo.put("type", delType);
		delInfo.put("delNo", delNo);
		
		return calendarDao.deleteCalendar(delInfo);
	}

	
	/**
	 * 일정 개수 조회
	 * @author 김영주
	 */
	@Override
	public int selectCalListCount(int ctgNo) {
		return calendarDao.selectCalListCount(ctgNo);
	}

	
	/**
	 * 학사 일정 조회
	 * author 김영주
	 */
	@Override
	public List<CalendarDto> selectUnivCalList() {
		return calendarDao.selectUnivCalList();
	}

	
	// 김영주 부분 끝-------------------------------------------------------

}
