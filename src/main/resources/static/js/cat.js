$(document).ready(function () {
			let allCat=JSON.parse(localStorage.getItem('food'));
			let table='';
			let totalprice=0;
			let totalcount=0;
			if(allCat!=null){
				$.each(allCat,function(index,cat){
					$.get("/getCatItem",{id:cat.id},function(data){
					table=table+`
					<tr>
							<th scope="row" >${index+1}</th>
							<td>${data.name}</td>
							<td>
								<img class="foodListImg"  src="${data.img}">
							</td>
							<td>${data.price} MMK</td>
							<td>
							<a class="btn btn-warning btn-sm"  onclick='minusItem(${cat.id})'><i class="fa fa-minus"></i></a>
							<span id="count${cat.id}">${cat.count}</span>
							<a class="btn btn-info btn-sm" onclick='addItem(${cat.id})'><i class="fa fa-plus"></i></a>
							<a class="btn btn-danger btn-sm" onclick='remove(${cat.id})'><i class="fa fa-trash"></i></a>
							</td>
						</tr>
					`;
					totalcount=cat.count+totalcount;
					totalprice=data.price*totalcount;
					document.getElementById('tbody').innerHTML=table+`
				<tr>
					<td  colspan="3">Total</td>
					<td><span id='totalprice'>${totalprice}</span> MMK</td>
					<td id="totalcount">${totalcount}
					</td>
				</tr>
				<tr>
					<td colspan="4"></td>
					<td id="totalcount">
						<a class="btn btn-danger" onclick='removeAll()'><i class="fa fa-times"></i></a>
						<a class="btn btn-success" onclick='confirm()'><i class="fa fa-check" aria-hidden="true"></i></a>
					</td>
				</tr>
				`;
	    		 });
				});	
			}	
		});

		function removeAll() {
			localStorage.clear();
			document.getElementById('tbody').innerHTML=``;
			document.getElementById("food-count").innerHTML=``;
		}
		
		function confirm() {
			$(document).ready(function () {
				let allCat=JSON.parse(localStorage.getItem('food'));
				$.each(allCat,function(index,cat){
					$.get("/confirm",{id:cat.id,count:cat.count},function(data){
						document.getElementById('alert').innerHTML=`
						<div class="alert alert-info text-center" role="alert">${data}</div>
						`;
					});	
				});
				localStorage.clear();
				document.getElementById('tbody').innerHTML=``;
				document.getElementById("food-count").innerHTML=``;
			});
		}
		
		function addItem(id) {
			let count;
			let totalcount=1;
			let totalprice=document.querySelector("#totalprice").textContent;
			let foods=JSON.parse(localStorage.getItem('food'));
			foods.forEach(food  => {
				totalcount=totalcount+food.count;
				if(food.id==id){
					count=++food.count;
				}
			});
			
			document.querySelector("#count"+id).innerHTML=count;
			localStorage.setItem("food",JSON.stringify(foods));
			document.querySelector("#totalcount").innerHTML=totalcount;
			$(document).ready(function () {
				$.get("/getCatItem",{id:id},function(data){
				totalprice=	data.price+parseInt(totalprice);
				document.querySelector("#totalprice").innerHTML=totalprice;
			});
		});				
	}

	function minusItem(id) {
		let count;
		let totalcount=-1;
		let totalprice=document.querySelector("#totalprice").textContent;
		let foods=JSON.parse(localStorage.getItem('food'));
		foods.forEach(food  => {
			totalcount=totalcount+food.count;
			if(food.id==id){
				count=--food.count;
			}
		});
		if(count<1){
			remove(id);
		}else{
			document.querySelector("#count"+id).innerHTML=count;
			localStorage.setItem("food",JSON.stringify(foods));
			document.querySelector("#totalcount").innerHTML=totalcount;
			$(document).ready(function () {
				$.get("/getCatItem",{id:id},function(data){
				totalprice=	data.price+parseInt(totalprice);
				document.querySelector("#totalprice").innerHTML=totalprice;
			});
		});
		}
}

		function remove(id) {
			let table='';
			let foods=JSON.parse(localStorage.getItem('food'));
			for (let i = 0; i < foods.length; i++) {
				if(foods[i].id==id){
					foods.splice(i, 1);
				
				}
			}
			localStorage.setItem("food",JSON.stringify(foods));
			$(document).ready(function () {
				let allCat=JSON.parse(localStorage.getItem('food'));
				let table='';
				let totalprice=0;
				let totalcount=0;
				if(allCat!=null){
					$.each(allCat,function(index,cat){
						$.get("/getCatItem",{id:cat.id},function(data){
						table=table+`
						<tr>
								<th scope="row" >${index+1}</th>
								<td>${data.name}</td>
								<td>
									<img class="foodListImg"  src="${data.img}">
								</td>
								<td>${data.price} MMK</td>
								<td>
								<a class="btn btn-warning btn-sm"  onclick='minusItem(${cat.id})'><i class="fa fa-minus"></i></a>
								<span id="count${cat.id}">${cat.count}</span>
								<a class="btn btn-info btn-sm" onclick='addItem(${cat.id})'><i class="fa fa-plus"></i></a>
								<a class="btn btn-danger btn-sm" onclick='remove(${cat.id})'><i class="fa fa-trash"></i></a>
								</td>
							</tr>
						`;
						totalcount=cat.count+totalcount;
						totalprice=data.price*totalcount;
						document.getElementById('tbody').innerHTML=table+`
					<tr>
						<td colspan="3">Total</td>
						<td><span id='totalprice'>${totalprice}</span> MMK</td>
						<td id="totalcount">${totalcount}</td>
					</tr>
					<tr>
					<td colspan="4"></td>
						<td id="totalcount">
							<a class="btn btn-danger" onclick='removeAll()'><i class="fa fa-times"></i></a>
							<a class="btn btn-success" onclick='confirm()'><i class="fa fa-check" aria-hidden="true"></i></a>
						</td>
					</tr>
					`;
					 });
					});
					
				}	
			});
			let length=JSON.parse(localStorage.getItem('food')).length;
			document.getElementById("food-count").innerHTML=length;
			if(length==0)document.getElementById('tbody').innerHTML="";
	}
		
		
		