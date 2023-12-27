import * as signIn from './signIn.js';

document.addEventListener('DOMContentLoaded', function () {
    signIn.checkLogOut();
    loadBills();
    loadMenus();
    loadTypes();
});

function loadBills() {
    const apiUrl = 'http://localhost:8080/api/bills';
  
    fetch(apiUrl)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(datas => {
        for (let i = 0; i < datas.data.length; i++) {
          const name = "Bill " + (i+1);
          const menus = datas.data[i].menus;
          const totalPrice = datas.data[i].totalPrice;
          const paymentStatus = datas.data[i].paymentStatus;
          const createDate = datas.data[i].createDate;
  
          addRowForBill(i+1, name, menus, totalPrice, paymentStatus, createDate);
        }
      })
      .catch(error => {
       
        console.error('There was a problem with the fetch operation:', error);
      });
  }
  
  function addRowForBill(num, name, menus, totalPrice, paymentStatus, createDate) {
    let menusString = "";
    let quantity = 0;
    for (const menu in menus) {
      if (menus.hasOwnProperty(menu)) {
        menusString += `${menu}: ${menus[menu]}<br>`;
        quantity += menus[menu];
      }
    }
    var row = "<tr> <td>" + num + "</td> <td>" + name + "</td> <td>" + menusString + "</td> <td >" + quantity + "</td> <td>" + totalPrice + "</td> <td>" + paymentStatus + "</td> <td>" + createDate + "</td> </tr>";
    $("#bill_table").append(row);
  }
  
  function loadMenus() {
    const apiUrl = 'http://localhost:8080/api/menus';
  
    fetch(apiUrl)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(datas => {
        for (let i = 0; i < datas.data.length; i++) {
          const name = datas.data[i].name;
          const description = datas.data[i].description;
          const price = datas.data[i].price;
          const types = datas.data[i].types;
        
          addRowForMenu(i+1,name, description, price, types);
        }
      })
      .catch(error => {
 
        console.error('There was a problem with the fetch operation:', error);
      });
  }
  
  function addRowForMenu(num, name, description, price, types) {
    let typesString = "";
    for (let i = 0; i < types.length; i++) {
          typesString+=types[i].type + "<br>";
    }
    var row = "<tr> <td>" + num + "</td> <td>" + name + "</td> <td>" + description + "</td> <td >" + price + "</td> <td>" + typesString + "</td> </tr>";
    $("#menu_table").append(row);
  }
  
  function loadTypes() {
    const apiUrl = 'http://localhost:8080/api/types';
  
    fetch(apiUrl)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(datas => {
        for (let i = 0; i < datas.data.length; i++) {
          const type = datas.data[i].type;
          addRowForType(i+1,type);
        }
      })
      .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
      });
  }
  
  function addRowForType(num,type) {
    var row = "<tr> <td>" + num + "</td> <td>" + type + "</td> </tr>";
    $("#type_table").append(row);
  }
