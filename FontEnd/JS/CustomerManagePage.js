document.addEventListener("DOMContentLoaded", function () {
    // token / role localStorage එකේ store කරලා තිබෙනවා කියලා assume කරමු
    const role = localStorage.getItem("role");


    const reportLink = document.getElementById("reportLink");
    const adLink = document.getElementById("advanceLink");


});


// ---- helpers
const fmt = n => (Number(n||0)).toFixed(2);

// populate years (current-3 .. current+2)
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

document.getElementById('btnBuild').addEventListener('click', buildCalendar);
document.getElementById('btnPrint').addEventListener('click', () => window.print());
document.getElementById('btnPay').addEventListener('click', () => {
    alert('Payment recorded (demo). Total: Rs ' + document.getElementById('grandTotal').textContent);
});

// Charges auto recalc
document.querySelectorAll('.charge').forEach(el=>{
    el.addEventListener('input', recalcTotals);
});
document.getElementById('advancePay').addEventListener('input', recalcTotals);

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
                inp.step='0.01';
                inp.placeholder='0.00';
                inp.dataset.day = dayNum;
                inp.className='dalu';
                inp.addEventListener('input', recalcTotals);
                tdAmt.appendChild(inp);
            }
            tr.appendChild(tdAmt);
        }
        tbody.appendChild(tr);
    }

    recalcTotals();
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

    // apply advancePay subtraction if any (as demo)
    const advance = Number(document.getElementById('advancePay').value || 0);

    document.getElementById('sumDalu').textContent = fmt(daluTotal);
    document.getElementById('sumCharges').textContent = fmt(charges);
    document.getElementById('grandTotal').textContent = fmt(daluTotal - advance - charges);
}

// initial state: auto set to current month
(function initToCurrent(){
    const now = new Date();
    document.getElementById('monthSel').value = String(now.getMonth()+1);
    buildCalendar();
})();


async function loadCustomerName() {
    try {
        const token = localStorage.getItem("token");// Login වෙද්දී save කරන JWT token

        const response = await fetch("http://localhost:8080/auth/me", {
            method: "GET",
            contentType: "application/json",
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (!response.ok) {
            throw new Error("Failed to fetch customer name");
        }

        const customerName = await response.text(); // Backend එක String return කරන නිසා
        console.log("Customer Name:", customerName);


        const nameSpan = document.querySelector(".header-title div span.hint");
        if (nameSpan) {
            nameSpan.textContent = customerName; // DemoEstate replace වෙනවා
        }

    } catch (error) {
        throw new Error("Failed to fetch customer name");
        showNotification(error, "error");
        console.error(error);
    }


}


document.addEventListener("DOMContentLoaded", loadCustomerName);



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



        document.getElementById("c_advance").value = data[0];
        document.getElementById("c_pohora").value = data[3];
        document.getElementById("c_rent").value = data[1];
        document.getElementById("c_other").value = data[2];
        document.getElementById("c_fine").value = data[4];

            alert("Error Load Details ds");


    }catch (error) {

        alert("Error Load Details22");
    }


}