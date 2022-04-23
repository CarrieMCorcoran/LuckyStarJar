import React from 'react';
//import fetch from 'fetch-blob';
import './App.css';

function App() {
  return ( 
  <div className="App">
    <h1> Lucky Star Jar</h1>

      <form id="form">
      <label htmlFor="jarName">Jar Name:</label>
        <input type="text" name="userName" placeholder = "Enter Your Name"/>
        <label htmlFor="email">Email:</label>
        <input type="text" name="email" placeholder = "Enter Email"/>
        <input type="submit" value="Create account">
          </input>
      </form>
    </div>
    
  );
}

/* function logSubmit(event) {
  log.textContent = `Form Submitted! Time stamp: ${event.timeStamp}`;
  event.preventDefault();
}

const form = document.getElementById('form');
const log = document.getElementById('log');
form.addEventListener('submit', logSubmit); */

// GET Request.
fetch('http://localhost:9080/star/system/controller/user/add')
    // Handle success
    .then(response => response.json())  // convert to json
    .then(json => console.log(json))    //print data to console
    .catch(err => console.log('Request Failed', err)); // Catch errors

export default App;
