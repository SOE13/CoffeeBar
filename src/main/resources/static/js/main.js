function addToCart(id) {
    
   let food;
   let test=true;
   let foods=localStorage.getItem("food");
   if(foods==null){
       foods=[];
   }else{
       foods=JSON.parse(foods);
   }
   if(foods.length==0){
       food={
           id:id,
           count:1
       }
       foods.push(food);
   }else{
       foods.forEach(food  => {
           if(food.id==id){
               food.count++;
                test=false;
           }
       });
       if(test){
        food={
            id:id,
            count:1
        }
        foods.push(food);
       }
   }
   localStorage.setItem("food",JSON.stringify(foods));
   let length=JSON.parse(localStorage.getItem("food")).length;
   document.getElementById("food-count").innerHTML=length;
}




let length1=JSON.parse(localStorage.getItem("food"));
if(length1!=null)document.getElementById("food-count").innerHTML=length1.length;
