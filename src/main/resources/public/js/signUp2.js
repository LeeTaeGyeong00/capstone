



// ajax 회원가입하기
const commentCreateBtn = document.querySelector("#join-btn");

//버튼 클릭 이벤트 감지
commentCreateBtn.addEventListener("click",function () {

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
            url: "/bangbang/auth/2/sign-in",  // Spring Boot 어플리케이션의 URL로 변경
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



})


// 이전 버튼
const backBtn = document.querySelector("#signUp-back");

backBtn.addEventListener("click", function () {
    window.location.href = "/bangbang/auth/sign-in";
})



// 전화번호 포멧
const userphone = document.querySelector("#user-phone").value
const formattedPhone = formatPhoneNumber(userphone);
document.querySelector("#js-phoneNum").textContent = formattedPhone;

function formatPhoneNumber(phoneNum) {
    return phoneNum.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
}



// 나이 포멧
const old = document.querySelector("#user-old").value
const formattedOld = formatOld(old)
document.querySelector("#js-birth").textContent = formattedOld

function formatOld(old) {
    return old.replace(/(\d{4})(\d{2})(\d{2})/, '$1년 $2월 $3일')
}
