/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId,obj) {
	if ($(obj).text() === "구독취소") {

		$.ajax({
			type: "delete",
			url: "/api/subscribe/"+toUserId,
			dataType: "json"
		}).done(res =>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error =>{
			console.log("구독취소 실패")
		});


	} else {
		$.ajax({
			type: "post",
			url: "/api/subscribe/"+toUserId,
			dataType: "json"
		}).done(res =>{
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error =>{
			console.log("구독하기 실패")
		});
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen() {
	$(".modal-subscribe").css("display", "flex");
}

function getSubscribeModalItem() {

}


// (3) 구독자 정보 모달에서 구독하기, 구독취소
function toggleSubscribeModal(obj) {
	if ($(obj).text() === "구독취소") {
		$(obj).text("구독하기");
		$(obj).toggleClass("blue");
	} else {
		$(obj).text("구독취소");
		$(obj).toggleClass("blue");
	}
}

// (4) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId,principalId) {
	if(pageUserId !== principalId){
		alert("프로필 사진을 수정할 수 없는 유저입니다.");
		return ;
	}

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		//서버에 이미지 전송

		let profileImageForm = $("#userProfileImageForm")[0];
		//form 태그 최상위 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
		let formData = new FormData(profileImageForm);

		$.ajax(
			{
				type: "put",
				url:`/api/user/${principalId}/profileImageUrl`,
				data:formData,
				contentType:false, // 필수 : x-www-form-urlincoded로 파싱되는 것을 방지
				processData:false, //필수  : contentType을 false로 줬을때 QueryString자동 설정되는 것을 해제
				enctype: "multipart/form-data",
				dataType:"json",
			}
		).done(res =>{
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
		}).fail(err =>{
			console.log("오류");
		});


	});
}


// (5) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (8) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






