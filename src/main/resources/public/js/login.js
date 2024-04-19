function check(){
    /* 아이디 유효성 검사 */
    if(myform.person_id.value.length === 0){
        alert("아이디를 입력해주세요.");
        myform.person_id.focus();
        return false;
    }


    /* 비밀번호 및 비밀번호 확인 유효성 검사 */
    if(myform.passwd.value.length === 0){
        alert("비밀번호를 입력해주세요.");
        myform.passwd.focus();
        return false;
    }


}
