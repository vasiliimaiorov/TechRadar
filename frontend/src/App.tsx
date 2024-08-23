import React from 'react';
import logo from './logo.svg';
import './App.css';


function App() {
  const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNzI0MzYxMTE3fQ.2CJW1ZZ_S7kmzDZmeag9YlLx6tDv-45kE0SBtH-lEXo'

  const tryPost = () => {
    fetch('http://localhost:8080/tech/createSolution', {
      method: 'POST',
      mode: 'cors',
      body: JSON.stringify({
        "name": "liz5",
        "documentationUrl": "url",
        "category": "LANGUAGES_FRAMEWORKS"
      }),
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': token,
      },
    });

  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <button style={{height: "200px", width: "200px"}} onClick={()=> tryPost()}>
          жми
        </button>
      </header>
    </div>
  );
}

export default App;
