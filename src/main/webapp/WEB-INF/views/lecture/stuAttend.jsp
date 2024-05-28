<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>출석</title>
  <style>
    /* div 스타일 (나중에 색 지울것) */
    .attend_wrap{
      width: 1000px;
      height: 1200px;
      margin: auto;
      display: flex;
      flex-direction: column;
      /*border: 1px solid black;*/
    }
  
    .attend_content1{
      height: 100px;
      display:flex;
      border: 1px solid lightgray;
    }
    .attend_title2{
      width: 600px;
      border: 3px solid skyblue;
    }
    .attend_title1, .attend_rate{
      width: 200px;
      border: 3px solid skyblue;
    }
  
    .attend_content2{
      display:flex;
      /*border: 1px solid lightgray;*/
    }
    .date{
      width: 70%;
      /*border: 3px solid gold;*/
    }
    .button{
      width: 30%;
      /*border: 3px solid palevioletred;*/
    }
    
    .attend_content3{
      height: 800px;
      display:flex;
      /*border: 3px solid greenyellow;*/
    }
  
    .attend_content4{
      height: 100px;
      display:flex;
      /*border: 1px solid lightgray;*/
    }
  	
  	#myTable td{padding: 8px}
  	
  </style>
</head>
<body>
	<div class="main-wrapper">
	
		<!-- 각 페이지 별 사이드 바 -->
    <jsp:include page="/WEB-INF/views/common/stuLecSidebar.jsp"/>
    
    <div class="page-wrapper">
    
    	<!-- 이쪽에 헤더(상단바) -->
	    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    	
	    <!----------------------------- 본문 시작 -------------------->
	    <div class="body-wrapper">
        <div class="container-fluid">
        <!-- ----------------------------- 실제 내용 작성 영역 ----------------------------- -->
          <!-- 페이지 타이틀 -->
          <div class="card bg-info-subtle shadow-none position-relative overflow-hidden mb-4">
            <div class="card-body px-4 py-3">
            
              <div class="row align-items-center my-3">
              
                <div class="col-9">
                  <h4 class="fw-semibold mb-8">출석</h4>
                  <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                      <li class="breadcrumb-item">
                        <a class="text-muted text-decoration-none" href="../main/index.html">Home</a>
                      </li>
                      <li class="breadcrumb-item" aria-current="page">출석</li>
                    </ol>
                  </nav>
                </div>
                
                <div class="col-3">
                  <div class="text-center mb-n5">
                    
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 페이지 내용 -->
          <div class="card">
        <div class="card-body">
          <div class="attend_wrap">
            <div class="attend_content1">
              <div class="attend_title1">X</div>
              <div class="attend_title2">
								 <c:choose>
	              	<c:when test="${empty leclist}">
	              		없음
	              	</c:when>
	              	<c:otherwise>
	              		<div class="form-group" style="width: 100px;">
								      <select class="form-select w-auto" id="lectureSelect" name="lectureSelect" onchange="handleChange();">
								      	<option value="0">강의선택</option>
								      	<c:forEach var="lec" items="${leclist}">
									    		<option value="${lec.lecNo}">${lec.lecName}
									    		</option>		
									    	</c:forEach>
								      </select>
						        </div>
	              	</c:otherwise>
	              </c:choose>
							</div>
              <div class="attend_rate">출석율</div>
            </div>
            <!-- 리스트 시작 -->
            <div class="attend_content3">
              <div class="list">
                <div class="card-body p-4">
                  <!-- 학생리스트 -->
                  <div class="result">
	                  <div class="table-responsive mb-4">
	                  <form id="stuListForm" method="post" action="updateStuAttned.do">
		                  <!-- 날짜 / 버튼 영역 -->
		                  <div class="d-flex flex-row justify-content-between" style="height:50px;">
			                  <!-- 날짜 -->
		                    <!-- start Input Type Date Time -->
		                    <div class="date" style="width: 400px; height: 100px;">
		                      <input type="date" class="form-control" id="dateArea"/>                
		                    </div>
		                    <!-- end Input Type Date Time -->
		                    
		                    
		                    
			                  <!-- 출석관련 버튼 -->
			                  <div class="buttons" style="display: flex; align-items: center;">
			                  	<select class="form-select w-auto attendance_option" name="stuAttendList[${status.index}].attendance" style="width:100px;">
									        	<option value="Y">출석</option>
									          <option value="N">결석</option>
									         	<option value="E">조퇴</option>
									          <option value="A">공결</option>
								          </select>
				                  <button type="button" class="btn btn-outline-info" id="bulkAttendSubmit" style="width:100px;">일괄변경</button>
			                    <button type="submit" class="btn btn-info" id="stuAttendSubmit" style="width:100px;">저장</button>
		                    </div>
	                    </div> 
	                    <table class="table border text-nowrap mb-0 align-middle app_doc_table overflow-hidden">
	                      <thead class="text-dark fs-4">
	                        <tr>
	                          <th>
	                            <input type="checkbox" class="checkAll" name="checkAll" id="checkAllBox" value="checkAllBox" onclick="selectAll(this)">
	                          </th>
	                          <th width="100px">
	                            <h6 class="fs-4 fw-semibold mb-0">번호</h6>
	                          </th>
	                          <th width="200px">
	                            <h6 class="fs-4 fw-semibold mb-0">사진</h6>
	                          </th>
	                          <th width="200px">
	                            <h6 class="fs-4 fw-semibold mb-0">학과</h6>
	                          </th>
	                          <th width="200px">
	                            <h6 class="fs-4 fw-semibold mb-0">학번</h6>
	                          </th>
	                          <th width="200px">
	                            <h6 class="fs-4 fw-semibold mb-0">이름</h6>
	                          </th>
	                          <th width="100px">
	                            <h6 class="fs-4 fw-semibold mb-0">출석</h6>
	                          </th>
	                        </tr>
	                      </thead>
	                      <tbody id="myTable">
	                        <c:choose>
		                        <c:when test="${ empty stulist }">
			                        <tr>
			                        	<td colspan="7"> 수강신청한 학생이 없습니다.</td>
			                        </tr>
		                        </c:when>
		                        <c:otherwise>
			                        <c:forEach var="a" items="${stulist}" varStatus="status">
				                        <tr>
					                        <td height="50px"><input type="checkbox" class="checkOne" name="checkOne" id="CheckOnebox" value="CheckOnebox"></td>
					                        <td>
						                        <input type="hidden" name="stuAttendList[${status.index}].lecNo" value="${a.lecNo}">
						                        <p class="mb-0 fw-normal fs-4">${status.count}</p>
					                        </td>
					                        <td>
					                        	<p class="mb-0 fw-normal fs-4">사진</p>
					                        </td>
					                        <td>
						                        <p class="mb-0 fw-normal fs-4">${a.majorName}</p>
						                        <input type="hidden" name="stuAttendList[${status.index}].majorNo" value="${a.majorNo}">
					                        </td>
					                        <td>
						                        <p class="mb-0 fw-normal fs-4" >${a.stuNo}</p>
						                        <input type="hidden" name="stuAttendList[${status.index}].stuNo" value="${a.stuNo}">
					                        </td>
					                        <td>
					                        	<p class="mb-0 fw-normal fs-4">${a.stuName}</p>
					                        </td>
					                        <td>
						                      	<div class="form-group" style="width: 100px;">
								                  		<select class="form-select w-auto attendance" name="stuAttendList[${status.index}].attendance">
									                			<option value="Y">출석</option>
									                  		<option value="N">결석</option>
									                			<option value="E">조퇴</option>
									               				<option value="A">공결</option>
								                  		</select>
							                    	</div>        
					                        </td>
				                        </tr>
			                        </c:forEach>
		                        </c:otherwise>
	                        </c:choose>
	                      </tbody>
	                    </table>
	                    </form>
	                  </div>
									</div>
									</div>
                </div>
              </div>
            </div>					
            <script>
              function changeActive(id){
                var id = $('#'+id);
                id.addClass("active")
              }
            </script>


            </div>
          </div>
        </div>

					<!-- ----------------------------- 실제 내용 작성 영역 end ----------------------------- -->
        </div>
      </div><!-- body-wrapper end 본문 끝-->
      
    </div><!-- page-wrapper end -->
    
    <!-- 세팅메뉴(바) -->
	    <jsp:include page="/WEB-INF/views/common/settingbar.jsp"/>
    
	</div><!-- main-wrapper end -->
	
	<!-- footer-->
  <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	<!-- 스크립트 -->
	<script>
	
	// handleChange 함수
	function handleChange() {
	  var selectElement = document.getElementById('lectureSelect');
	  var lecNo = selectElement.value; // 선택된 강의의 번호
	
	//빈 문자열이거나 숫자가 아닌 경우 처리
	  if (!lecNo || isNaN(parseInt(lecNo))) {
	    console.error('Invalid Lecture Number:', lecNo);
	    return; // 올바르지 않은 경우 처리 중단
	  }else{
		// AJAX 호출
			$.ajax({
				url: '${contextPath}/lec/stuListForLec.do', // 서버 URL
				method: 'GET',
				data: {lecNo:lecNo}, // 선택된 강의 번호를 전달
				success: function(result) {
									let tbody = "";
									console.log(result);
									
									if(result.length > 0){
										
										for(let i=0; i<result.length; i++){
											
											
											tbody  += "<tr>"
															+ 	"<td height='50px'>" + "<input type='checkbox' class='checkOne' name='checkOne' id='CheckOnebox' value='CheckOnebox'>" + "</td>"
															+ 	"<td><input type='hidden' name='stuAttendList[" + i + "].lecNo' value='" + result[i].lecNo + "'><p class='mb-0 fw-normal fs-4'>" + (i + 1) + "</p></td>"
															+ 	"<td><p class='mb-0 fw-normal fs-4'>" + result[i].사진 + "</p></td>"
															+ 	"<td><input type='hidden' name='stuAttendList[" + i + "].majorNo' value='" + result[i].majorNo +"'><p class='mb-0 fw-normal fs-4'>" + result[i].majorName + "</p></td>" 
															+ 	"<td><input type='hidden' name='stuAttendList[" + i + "].stuNo' value='" + result[i].stuNo+"'><p class='mb-0 fw-normal fs-4'>" + result[i].stuNo + "</p></td>"													
															+ 	"<td><p class='mb-0 fw-normal fs-4'>" + result[i].stuName + "</p></td>"             
															+ 	"<td>"  
										          +   	"<div class='form-group' style='width: 100px;'>" 
										          +				"<select class='form-select w-auto' name='stuAttendList[" + i + "].attendance'>" 
										          +					"<option value='Y'>출석</option>" 
										          +					"<option value='N'>결석</option>"
										          +					"<option value='E'>조퇴</option>" 
										          +					"<option value='A'>공결</option>" 
										          +				"</select>" 
										          +			"</div>"
										          + 	"</td>"
						                  + "</tr>";							
															
										}
										
									}else{
										tbody += "<tr><td colspan='7'>수강신청한 학생이 없습니다.</td></tr>";
									}
									
						
									$("#myTable").html(tbody);
		            },
			error: function(xhr, status, error) {
							console.error(xhr, status, error); // 에러 처리
	        console.log("ajax 통신실패");
	      }
	  });
	  }
	  
	  console.log("handleChange selectElement :",selectElement);
	  console.log("handleChange lecno :", lecNo);
	}  
	 
								    // 서버에서 받은 출석 리스트를 화면에 표시하는 함수
								    function displayStuAttendList(data) {
								        // 받은 데이터를 화면에 표시하는 코드 작성
								        $('#StuAttendList').html(data);
								    }
	
								    // 체크박스
								    $(document).ready(function(){
										// th체크박스 클릭 시, 나머지 체크박스도 선택되게 
										$("#checkAllBox").click(function(){
										var checkAll=$("#checkAllBox").prop("checked");

											if(checkAll){
												$(".checkOne").prop("checked",true);
										    }
										    else{
										        $(".checkOne").prop("checked",false);
										    }
										});
										
										$("#bulkAttendSubmit").on("click", function(){
											$(".checkOne").each(function(index, el){
												if($(el).prop("checked")){
													console.log($(el).parent().parent().children().eq(6).children().children().val($(".attendance_option").val()));
												}
											})											
										})

										
										$('#myTable').on('change', '.checkOne', function() {
										    var checkOne = $(".checkOne").length;
										    var checkedCheckOne = $(".checkOne:checked").length;

										    // 체크박스가 모두 선택되었을 때 상위 체크박스 선택되도록 설정
										    if (checkOne == checkedCheckOne) {
										        $("#checkAllBox").prop("checked", true);
										    } else {
										        $("#checkAllBox").prop("checked", false);
										    }
										});
										
										});
								    
								    // 날짜
								    // 오늘 날짜를 가져와서 YYYY-MM-DD 형식의 문자열로 변환
    var today = new Date().toISOString().split('T')[0];

    // 기본 날짜를 오늘 날짜로 설정
    document.getElementById('dateArea').value = today;
								    
								    </script>
</body>
</html>