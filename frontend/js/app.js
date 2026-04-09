/* ===============================
STUDENT LOGIN
================================ */

async function loginStudent(){

let hallTicket = document.getElementById("hallTicket").value
let password = document.getElementById("password").value

try{

let response = await fetch("http://localhost:8080/student/login",{
method:"POST",
headers:{ "Content-Type":"application/json" },
body:JSON.stringify({
hallTicketNumber:hallTicket,
password:password
})
})

if(!response.ok){
throw new Error("Invalid Login")
}

let student = await response.json()

localStorage.setItem("studentRoll",student.hallTicketNumber)
localStorage.setItem("studentYear",student.year)
localStorage.setItem("admissionType",student.admissionType)

window.location.href="student.html"

}catch(error){

alert("Invalid ID or Password")

}

}


/* ===============================
SHOW WELCOME MESSAGE
================================ */

function showWelcome(){

let roll = localStorage.getItem("studentRoll")
let welcomeText = document.getElementById("welcomeStudent")

if(welcomeText && roll){
welcomeText.innerText = "Welcome " + roll
}

}


/* ===============================
LOGOUT
================================ */

function logout(){

localStorage.clear()
window.location.href="index.html"

}


/* ===============================
LOAD SEMESTERS TABLE
================================ */

function loadSemesters(){

let table = document.getElementById("semesterTable")

if(!table) return

let year = parseInt(localStorage.getItem("studentYear"))
let admissionType = localStorage.getItem("admissionType")

let semesters=[
"1-1","1-2",
"2-1","2-2",
"3-1","3-2",
"4-1","4-2"
]

table.innerHTML=""

semesters.forEach((sem,index)=>{

let semesterNumber=index+1
let status=""
let action=""

let amount = admissionType==="M" ? 12000 : 8750

if(semesterNumber < (year*2 -1)){

status="PAID"
action="✔"

}
else if(semesterNumber === (year*2 -1)){

status="Pay Now"

action=`<button onclick="payFee(${amount},'${sem}')">
Pay ₹${amount}
</button>`

}
else{

status="To be paid later"
action="Locked"

}

let row=`
<tr>
<td>${sem}</td>
<td>₹${amount}</td>
<td>${status}</td>
<td>${action}</td>
</tr>
`

table.innerHTML+=row

})

}


/* ===============================
PAYMENT REDIRECT
================================ */


function payFee(semester,amount){

// store payment info
localStorage.setItem("payAmount", amount)
localStorage.setItem("paySemester", semester)

// redirect to payment page
window.location.href = "payment.html"

}

function confirmPayment(semester){

let hallTicket = localStorage.getItem("studentRoll")

fetch(`http://localhost:8080/student/pay/${hallTicket}/${semester}`,{
method:"PUT"
})
.then(res => res.text())
.then(msg=>{
alert(msg)
location.reload()
})

}
/* ===============================
AUTO LOAD WHEN PAGE OPENS
================================ */

document.addEventListener("DOMContentLoaded",function(){

showWelcome()
loadSemesters()

})