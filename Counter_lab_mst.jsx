import React, { useState } from 'react'

const Counter = () => {
    let [cnt,setCnt]=useState(0);
    function handleInc(){
       if(cnt==10){
        alert("Maximum limit reached");
        return;
       }else{
        setCnt(cnt+1);
       }
      }

     function handleDec(){
       if(cnt==0){
        alert("Minimum limit reached");
        return;
       }else{
        setCnt(cnt-1);
       }
      }
  return (
    <div>
      <h1>{cnt}</h1>
      <button onClick={handleInc}>Increment</button>
      <button onClick={handleDec}>Decrement</button>
    </div>
  )
}

export default Counter
