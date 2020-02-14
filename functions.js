var meno = "http://localhost:8080/TesticekNatentoTyzdnicek/rest/data/"
var logData;
var meno;
var heslo;
var subDataVerzie = [];

function vloz(v){
		document.getElementById("abc").placeholder=v;
}

function ses(){ //nastavenie session, nefunguje zatiaľ :D
	meno = document.getElementById("uname").value;
	heslo = document.getElementById("psw").value;
	sessionStorage.setItem("name", "meno");
}
function nacitaj(){ //nacitava data z allProdkt
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "allprodukt";
	var data = {FromDate: document.getElementById("datumOd").value,
				ToDate: document.getElementById("datumDo").value,
				Zeichnungsnummer: document.getElementById("cisloDielu").value,
				Kunde: document.getElementById("zakaznik").value,
				Bezeichnung: document.getElementById("umiestnenieDielu").value,
				zostava: document.getElementById("cisloZostavy").value, 
				nrNumber: document.getElementById("cisloObjednavky").value};
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			 var data2 = JSON.parse(this.responseText);
			 vypis(data2);
		}
	}
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	var tmp = "";
	for (var key in data) {
		if (data.hasOwnProperty(key)) {
			tmp += key + "=" + data[key] + "&";
		}
	}
	tmp = tmp.substring(0,tmp.length-2);
	xmlhttp.send(tmp);
	return false;
}

function log(){ // pripojenie sa na log a stiahnutie údajov
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "log";
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
			logData = data2;
			vypisLog(data2);
		}
	}
	xmlhttp.open("GET", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlhttp.send(null);
	return false;
}

function login(){ //login nie ? :D 
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "user";
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
			if (data2 == true){
				ses();
				window.open("main-screen.html","_self")
			}
			else {
				window.alert("Zlé prihlasovacie meno alebo heslo");
			}
		}
	}
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	var tmp = "uname=" + document.getElementById("uname").value + "&pass=" + document.getElementById("psw").value;
	xmlhttp.send(tmp);
	return false;
}

function newProduct(){ //vytvorenie nového produktu
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "newProdukt";
	var data = {Bezeichnung: document.getElementById("umiestnenieGet").value,
				Kunde: document.getElementById("zakaznikGet").value,
				Zeichnungsnummer: document.getElementById("cisloVykresuGet").value,	
				Cislo: document.getElementById("cisloObjedavkyGet").value,
				Zostava: document.getElementById("zostavaGet").value, 
				User: "admin"};
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			window.alert(this.responseText);
		}
	}
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	var tmp = "";
	for (var key in data) {
		if (data.hasOwnProperty(key)) {
			tmp += key + "=" + data[key] + "&";
		}
	}
	tmp = tmp.substring(0,tmp.length-2);
	xmlhttp.send(tmp);
	return true;
}

function fillData(){ //prečítanie Excelu a vyplnenie polí
	let excel = document.getElementById("excel").files[0];  // file from input
	let xmlhttp = new XMLHttpRequest();
	let formData = new FormData();
	
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
			document.getElementById("umiestnenieGet").value = data2["umiestnenieGet"];
			document.getElementById("zakaznikGet").value = data2["zakaznikGet"];
			document.getElementById("cisloVykresuGet").value = data2["cisloVykresuGet"];
			document.getElementById("cisloObjedavkyGet").value = data2["cisloObjedavkyGet"];
			document.getElementById("zostavaGet").value = data2["zostavaGet"];
		}
	}
	formData.append("file", excel);                                
	xmlhttp.open("POST", meno+"Excel");
	xmlhttp.send(formData);
	return false;
}

function download() { //sťahovanie logu
	var exportData = "";
	var today = new Date();
	var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
	var today = new Date();
	var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
	
	for (var i = 0; i < logData.length; i++){
		for (var key in logData[i]){
			exportData += logData[i][key] + " ";
		}
		exportData += "\n";
	}
	
    var file = new Blob([exportData], {type: "text/plain"});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, "log " + date + " " + time + ".txt");
    else { // Others
        var a = document.createElement("a"),
                url = URL.createObjectURL(file);
        a.href = url;
        a.download = "log " + date + " " + time + ".txt";
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);  
        }, 0); 
    }
	return false;
}

function setDatum(id){ //nastavenie dátum
	var a = document.getElementById("ddatum").value;
	let xmlhttp = new XMLHttpRequest();
	let formData = new FormData();
	
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
		}
	}                               
	xmlhttp.open("POST", meno+"novyDatum");
	xmlhttp.send(formData);
	return false;
}

function nacitajVerzie(zeichN){ //nacitava verzie
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "verzie";
	zeichN = "111.___.333.444";	
	
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			 var data2 = JSON.parse(this.responseText);
			 subDataVerzie = data2;
		}
	}
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlhttp.send("zeich=" + zeichN);
	return false;
}

function showSetters(){
	document.getElementById("settery").style.display= "block";
	return false;
}

function hideSetters(){
	document.getElementById("settery").style.display = "none";
	return false;
}

function vypis(data){ //vypísanie dát 
	var tab = document.getElementById("tableBody");
	tab.innerHTML = '';
	var riadok;
	var bunka;
	var text;
	
	for (var i  = 0; i < data.length; i++){
		var diel = data[i];

		riadok=document.createElement("tr");			
		bunka=document.createElement("td");
		text=document.createTextNode(diel["p.Zeichnungsnummer"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["Kunde"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["Bezeichnung"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["NR"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);
		
		/*bunka=document.createElement("td");
		if (diel["Datum"] != null){
			text=document.createTextNode(diel["Datum"].substring(0,10));
		}
		else {
			text=document.createTextNode("");
		}*/
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createElement("BUTTON");
		//text.setAttribute("onclick","openFormUprav('"+diel["meno"]+" "+diel["priezvisko"]+"',"+diel["id"]+")");
		text.innerText="Odstrániť";
		bunka.appendChild(text);
		
		text=document.createElement("BUTTON");
		//text.setAttribute("onclick","zmaz_zamestnanca('"+diel["id"]+"')");
		text.innerText="Stiahnuť";
		bunka.appendChild(text);
		
		/*text=document.createElement("BUTTON");
		var a = diel["p.Zeichnungsnummer"];
		text.setAttribute("id",a);
		text.setAttribute("onclick","setDatum(this.id)");
		text.innerText="Nastaviť dátum";
		bunka.appendChild(text);*/
		
		text=document.createElement("BUTTON");
		//text.setAttribute("onclick","zmaz_zamestnanca('"+diel["id"]+"')");
		text.innerText="Otvoriť na disku";
		bunka.appendChild(text);
		
		bunka.appendChild(text);
		riadok.appendChild(bunka);
		tab.appendChild(riadok);
		
		//Tu pridavam verzie pod 
		//riadok.setAttribute("onclick","zobraz(\'z"+ i +"\')");
		tab.appendChild(riadok);
	}
}

function zobraz(meno){
     var el=document.getElementById(meno);
     if (el.style.display=="none"){
             el.style.display="table-row";
     }
     else{
        el.style.display="none";
     }
}

function vypisLog(data){ //vypisanie logu
	var tab = document.getElementById("tableBody");
	tab.innerHTML = '';
	var riadok;
	var bunka;
	var text;
	
	for (var i  = 0; i < data.length; i++){
		var diel = data[i];
		//var kontakty=diel["kontakty"];

		riadok=document.createElement("tr");

		bunka=document.createElement("td");
		text=document.createTextNode(diel["user"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["updated_what"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["updated_how"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["updated_at"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		tab.appendChild(riadok);
	}
}

function dajRow(){
	
	$('#table').find('tr').click( function(){
		//console.log(($(this).index()+1) );
	});
	
	console.log($(this).closest('tr').find('td:eq(0)').text());
}

