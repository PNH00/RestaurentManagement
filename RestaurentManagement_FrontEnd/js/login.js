import * as signUp from './signUp.js';
import * as signIn from './signIn.js';

$(document).ready(function(){

  $("#name").blur(signUp.validateUserName)

  $("#email").blur(signUp.validateEmail)

  $("#password").blur(signUp.validatePassword)

  $("#btnSave").click(signUp.signUp)

  $("#nameSignIn").blur(signIn.validateUserNameSignIn)

  $("#passwordSignIn").blur(signIn.validatePasswordSignIn)

  $("#btnSaveSignIn").click(signIn.signIn)


  function abc(){
      if(signIn.checkSignIn){
        console.log(signIn.checkSignIn)
        $('#insert').attr('data-target','#myModalInsert');
        $('#update').attr('data-target','#myModal');
      }else{
        window.alert("You must sign in first!");
      }
  }
  $("#insert").click(abc)
  $('#update').click(abc)

  $('#logout').click(signIn.logOut)
})
