export let checkSignIn = false;

export async function signInAccount(userName, password) {
    const options = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userName: userName, password: password})
    };
    const apiUrl = 'http://localhost:8080/api/accounts/login';
  
    try {
      const response = await fetch(apiUrl, options);
  
      if (!response.ok) {
        return { success: false, message: 'Wrong user name or password!' };
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
  
  export function validateUserNameSignIn(){
    var name=$("#nameSignIn").val();
    var re = /^[a-zA-Z0-9]{1,15}$/;
    if(name!=""){
        if(re.test(name)){
            $("#checkUserNameSignIn").html("")
            return true;
        }else{
            $("#checkUserNameSignIn").html(" User name must be no longer than 15 characters and contain only letters and numbers!")
            return false;
        }
    }else $("#checkUserNameSignIn").html("This field is empty!")
  }
  
  export function validatePasswordSignIn(){
    var password=$("#passwordSignIn").val();
    var re=/^(?=.*[A-Z])(?=.*\d).{8,}$/;
    if(password!=""){
        if(re.test(password)){
            $("#checkPasswordSignIn").html("")
            return true;
        }else{
            $("#checkPasswordSignIn").html("Password format is incorrect!")
            return false;
        }
    }else $("#checkPasswordSignIn").html("This field is empty!")
  }
  
  export async function signIn() {
    var userName = $("#nameSignIn").val();
    var password = $("#passwordSignIn").val();
    
    if (validatePasswordSignIn() && validateUserNameSignIn()) {
      let result = await signInAccount(userName, password);
      
      if (!result.success) {
        window.alert(result.message);
        console.log(result);
        $("#nameSignIn").val("");
        $("#passwordSignIn").val("");
      } else {
        $("#myModalSignIn").modal("hide");
        
        $("#nameSignIn").val("");
        $("#passwordSignIn").val("");
        window.alert("Sign in successfully");

        localStorage.setItem("userName", userName);
        
        $('#loginDropdown').css("visibility", "hidden");
        $("#logoutLi").css("visibility", "visible");
        checkSignIn = true
        console.log(checkSignIn)
        console.log(result);
      }
    }
  }

  export async function logOut() {
        localStorage.removeItem('userName');
       
        $('#logoutLi').css("visibility", "hidden");
        $('#loginDropdown').css("visibility", "visible");
  }

  export async function checkLogOut() {
    if(localStorage.getItem("userName")!==null){
      $('#loginDropdown').css("visibility", "hidden");
      $("#logoutLi").css("visibility", "visible");
      checkSignIn = true
      console.log(checkSignIn)
      console.log(result);
    }
}