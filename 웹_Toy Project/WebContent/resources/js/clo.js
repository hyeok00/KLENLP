
var visitors=(function(){
   var count=0;
   function addCount() {
      return count=count+1;
   }
   function getCount() {
      return count;
   }
   function setCount(num) {
      count=num;
   }
   return {
      addCount : function(){
         return addCount();
      },
      getCount : function() {
         return getCount();
      },
      setCount : function(num) {
         setCount(num)
      }
   };
}());

visitors.setCount(1);