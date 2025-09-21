let good = 0;
let gold = 0;

let allGiveCount = 0;

document.addEventListener("DOMContentLoaded", function () {

    const role = localStorage.getItem("role");


    const reportLink = document.getElementById("reportLink");
    const adLink = document.getElementById("advanceLink");


});


// ---- helpers
const fmt = n => (Number(n||0)).toFixed(2);

(function fillYears(){
    const ySel = document.getElementById('yearSel');
    const y = new Date().getFullYear();
    for(let i=y-3;i<=y+2;i++){
        const opt = document.createElement('option');
        opt.value = i; opt.textContent = i;
        if(i===y) opt.selected = true;
        ySel.appendChild(opt);
    }
})();


document.querySelectorAll('.charge').forEach(el=>{
    el.addEventListener('input', recalcTotals);
});

function daysInMonth(year, month){ // month 1..12
    return new Date(year, month, 0).getDate();
}

function buildCalendar(){
    const y = +document.getElementById('yearSel').value;
    const m = +document.getElementById('monthSel').value;
    if(!y || !m){ alert('Select Year & Month'); return; }

    const monthNames = ['January','February','March','April','May','June','July','August','September','October','November','December'];
    document.getElementById('headerMonth').textContent = `මාසය – ${monthNames[m-1]} ${y}`;

    const dCount = daysInMonth(y, m);
    const colChunk = Math.ceil(dCount/3); // split into 3 columns
    const columns = [[],[],[]];
    let d=1;
    for(let c=0;c<3;c++){
        for(let r=0; r<colChunk && d<=dCount; r++, d++){
            columns[c].push(d);
        }
    }


    const maxRows = Math.max(columns[0].length, columns[1].length, columns[2].length);
    const tbody = document.getElementById('calBody');
    tbody.innerHTML='';

    for(let r=0;r<maxRows;r++){
        const tr = document.createElement('tr');
        for(let c=0;c<3;c++){
            const dayNum = columns[c][r];
            // day cell
            const tdDay = document.createElement('td');
            tdDay.textContent = dayNum ? dayNum : '';
            tr.appendChild(tdDay);

            // amount cell (input)
            const tdAmt = document.createElement('td');
            if(dayNum){
                const inp = document.createElement('input');
                inp.type='number';
                inp.placeholder='0.00';
                inp.className='dalu';
                inp.dataset.day = dayNum;
                tdAmt.appendChild(inp);
                inp.readOnly = true;
            }
            tr.appendChild(tdAmt);
        }
        tbody.appendChild(tr);
    }


}

function recalcTotals(){
    // sum dalu inputs
    let daluTotal = 0;
    document.querySelectorAll('input.dalu').forEach(i=>{
        daluTotal += Number(i.value||0);
    });

    // sum charges
    let charges = 0;
    document.querySelectorAll('.charge').forEach(i=> charges += Number(i.value||0) );

}

// initial state: auto set to current month
(function initToCurrent(){
    const now = new Date();
    document.getElementById('monthSel').value = String(now.getMonth()+1);
    buildCalendar();
})();


async function loadCustomerNames(id) {

    try {
        const token = localStorage.getItem("token");// Login වෙද්දී save කරන JWT token

        const response = await fetch(`http://localhost:8080/auth/LoadName/${id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const customerName = await response.text();

        if (!response.ok || customerName === "Customer ID Not Found") {
            showNotification(customerName, "error");
            return;
        }


        const nameSpan = document.querySelector(".header-title div span.hint");
        if (nameSpan) {
            nameSpan.textContent = customerName;
        }

    } catch (error) {
        throw new Error("Failed to fetch customer name");
        showNotification(error, "error");
        console.error(error);
    }


}



document.getElementById("OKbtn").addEventListener("click", function(){
    const id = document.getElementById("CUSTID").value;

if (id === null || id === ""){
        CUSTID.style.borderColor="red";
        return;
    }

    loadCustomerNames(id)

});


document.getElementById("btnBuild").addEventListener("click", () =>{
    const month = document.getElementById("monthSel");
    const selectedMonthText = month.options[month.selectedIndex].text;

    const year = document.getElementById("yearSel").value;
    const date = selectedMonthText+year;


    loadDetails(date);

});


async function loadDetails(date) {


    const hint = document.querySelector(".hint");
    const name = hint.textContent;

    try {
        const response = await fetch(`http://localhost:8080/auth/loaddetailsCUS/${name}/${date}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {

            alert("Error Load Details");
            return;
        }


        const data = await response.json();



        for (let i = 0; i < data.length; i++) {
            if (data[i] === null){
                data[i] = 0;
            }
        }


        document.getElementById("c_advance").value = data[0];
        document.getElementById("c_pohora").value = data[3];
        document.getElementById("c_rent").value = data[1];
        document.getElementById("c_other").value = data[2];
        document.getElementById("c_fine").value = data[4];
        document.getElementById("l1").textContent = data[5];
        document.getElementById("l2").textContent = data[6];

        allGiveCount = (data[0]+data[1]+data[2]+data[3]+data[4]+25);



        good = data[5];
        gold = data[6];

        try {
            const response = await fetch(`http://localhost:8080/auth/loaddTeaLeaf/${name}/${date}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            const data = await response.json();

            console.log(data);

            if (!response.ok) {

                alert("Error Load Details");
                return;
            }

            const inputs = document.querySelectorAll('input.dalu');
            inputs.forEach(inp => {
                const day = parseInt(inp.dataset.day); // day number stored in dataset
                if(day && data[day] !== undefined){
                    inp.value = data[day]; // assign value from backend
                }
            });


            recalcTotals();

            try {
                const response = await fetch(`http://localhost:8080/auth/loaddTeaLeafCount/${name}/${date}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                const data1 = await response.json();


                if (!response.ok){
                    alert("Error Load Details");
                }

                document.getElementById("l3").textContent = data1[0];
                document.getElementById("l4").textContent = data1[1];

                const tot = data1[0] + data1[1];
                document.getElementById("TotCount").value = tot;

                const total = (good*data1[0]+gold*data1[1]);
                document.getElementById("c_disc").value = total;
                document.getElementById("sumDalu").textContent = total;

                if (allGiveCount > total){
                    const tm = allGiveCount - total;
                    document.getElementById("advancepay").textContent = tm;
                    advancepay.style.color ="red";
                }
                else {
                    const tt = total-allGiveCount;
                    document.getElementById("sumCharges").textContent = tt;
                    sumCharges.style.color = "blue"
                }


            }catch (error) {

                alert("Error Load Detailsdaw");
            }

        }catch (error) {

            alert("Error Load Detailsdwqwq");
        }

        document.getElementById("grandTotal").textContent = allGiveCount;

    }catch (error) {

        alert("Error Load Details22");
    }


}