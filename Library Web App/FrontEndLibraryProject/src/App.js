import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BookContainer} from "./components/books/BookContainer"
import 'bootstrap/dist/css/bootstrap.min.css';
function App() {
  return (
    <div className="App">
      <BookContainer/>
    </div>
  );
}

export default App;
