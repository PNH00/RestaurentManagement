export function validateUserName(){
    var name=$("#name").val();
    var re = /^[a-zA-Z0-9]{1,15}$/;
    if(name!=""){
        if(re.test(name)){
            $("#checkUserName").html("")
            return true;
        }else{
            $("#checkUserName").html(" User name must be no longer than 15 characters and contain only letters and numbers!")
            return false;
        }
    }else $("#checkUserName").html("This field is empty!")
  }
  
  export async function createAccount(userName, password, email) {
    const options = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userName: userName, password: password, email: email })
    };
    const apiUrl = 'http://localhost:8080/api/accounts/signup';
  
    try {
      const response = await fetch(apiUrl, options);
  
      if (!response.ok) {
        return { success: false, message: 'Uer name or email already exists!' };
      }
  
      const data = await response.json();
  
      if (data.error) {
        return { success: false, message: data.error };
      }
  
      console.log(data.data);
      return { success: true, data: data.data };
    } catch (error) {
      console.error('There was a problem with the fetch operation:', error);
      return { success: false, message: 'Something went wrong' };
    }
  }

  export function validateEmail(){
    var email=$("#email").val();
    var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if(email!=""){
      if(regex.test(email)){
        $("#checkEmail").html("")
        return true;
      }else{
            $("#checkEmail").html("Email'format is incorrect!")
            return false;
        }
    }
    else {
        $("#checkEmail").html("This field is empty!")
        return false;
    }
  }
  
  export function validatePassword(){
    var password=$("#password").val();
    var re=/^(?=.*[A-Z])(?=.*\d).{8,}$/;
    if(password!=""){
        if(re.test(password)){
            $("#checkPassword").html("")
            return true;
        }else{
            $("#checkPassword").html("Password format is incorrect!")
            return false;
        }
    }else $("#checkPassword").html("This field is empty!")
  }
  
  export async function signUp() {
    var userName = $("#name").val();
    var email = $("#email").val();
    var password = $("#password").val();
  
    if (validateEmail() && validatePassword() && validateUserName()) {
      let result = await createAccount(userName, password, email);
  
      if (!result.success) {
        window.alert(result.message);
        console.log(result);
        $("#name").val("");
        $("#email").val("");
        $("#password").val("");
      } else {
        $("#myModal").modal("hide");
        $("#name").val("");
        $("#email").val("");
        $("#password").val("");
        window.alert("Sign up successfully");
        console.log(result);
      }
    }
  }