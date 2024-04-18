function check(){
    /* 아이디 유효성 검사 */
    if(myform.person_id.value.length === 0){
        alert("아이디를 입력해주세요.");
        myform.person_id.focus();
        return false;
    }

    if(myform.person_id.value.length <5 || myform.person_id.value.length > 12){
        alert("아이디는 5자 이상, 12자 이하로 입력 가능합니다.");
        myform.person_id.focus();
        return false;
    }

    if(!checkEngNumber(myform.person_id.value)){
        alert("아이디는 영어 대소문자, 숫자로만 입력 가능합니다.");
        myform.person_id.focus();
        return false;
    }


    /* 비밀번호 및 비밀번호 확인 유효성 검사 */
    if(myform.passwd.value.length === 0){
        alert("비밀번호를 입력해주세요.");
        myform.passwd.focus();
        return false;
    }

    if(myform.passwd2.value.length === 0){
        alert("비밀번호 확인을 입력해주세요.");
        myform.passwd2.focus();
        return false;
    }

    if(myform.passwd.value !== myform.passwd2.value){
        alert("비밀번호를 재확인하세요.");
        myform.passwd2.select();
        return false;
    }

    if(myform.passwd.value.length < 8 || !checkEngNumSpeChar(myform.passwd.value)){
        alert("비밀번호는 영문,숫자,특수문자 포함 8자이상 입력해야 합니다.");
        myform.passwd.select();
        return false;
    }


    /* 닉네임 유효성 검사 */
    if(myform.nick_name.value.length === 0){
        alert("닉네임을 입력해주세요.");
        myform.nick_name.focus();
        return false;
    }

    if(myform.nick_name.value.length < 2 || myform.nick_name.value.length > 10){
        alert("닉네임은 2자 이상, 10자 이하로 입력이 가능합니다. ");
        myform.nick_name.focus();
        return false;
    }


    /* 나이 유효성 검사 */
    if(myform.old.value.length === 0) {
        alert("나이를 입력해주세요.");
        myform.old.focus();
        return false
    }

    if(isNaN(myform.old.value)){
        alert("나이는 숫자만 입력가능합니다.");
        myform.old.focus();
        return false;
    }


    /* 전화번호 유효성 검사 */
    if(myform.phone_num.value.length === 0){
        alert("전화번호를 입력해주세요.");
        myform.phone_num.focus();
        return false;
    }


    if(isNaN(myform.phone_num.value) || myform.phone_num.value.length !== 11){
        alert("전화번호는 형식에 맞춰 숫자로만 입력해주세요.");
        myform.phone_num.focus();
        return false;
    }


    // 문자열이 영어, 숫자인지 확인하는 메서드
    function checkEngNumber(value) {
        return /^[a-zA-Z0-9]+$/.test(value);
    }

    // 문자열이 영어, 숫자, 특수문자인지 확인하는 메서드
    function checkEngNumSpeChar(value) {
        var regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?\/\\~-])/;
        return regex.test(value);
    }


}



    //댓글 생성 버튼 변수화
    const commentCreateBtn = document.querySelector("#join-btn");

    //버튼 클릭 이벤트 감지
    commentCreateBtn.addEventListener("click",function () {


    // check 함수 호출하여 반환값 받음
    const validationResult = check();

    // 반환값에 따라 동작 분기
    if (validationResult === false) {

} else {
    // 보낼 JSON 데이터 생성
    var jsonData = {
    person_id: document.querySelector("#user-id").value,
    passwd: document.querySelector("#user-passwd").value,
    nick_name: document.querySelector("#nick-name").value,
    old: document.querySelector("#user-old").value,
    phone_num: document.querySelector("#user-phone").value
};

    // Ajax 요청
    $.ajax({
    type: "POST",
    url: "/bangbang/auth/sign-in",  // Spring Boot 어플리케이션의 URL로 변경
    contentType: "application/json",
    data: JSON.stringify(jsonData),
    success: function(response) {

    // 서버에서의 응답을 처리
    console.log(response);
    const msg =  "회원가입이 되었습니다"
    alert(msg);
    window.location.href = "/bangbang/auth/sign-up";
},
    error: function(error) {

    const msg =  "회원가입 실패"
    alert(msg);
    console.error("에러 발생:", error);
    window.location.href = "/bangbang/auth/sign-in";
}
});
}



})
