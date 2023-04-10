import './App.css';
import { Route, Routes } from "react-router-dom"
import { useState } from "react"
import Login from './page-views/Login'
import Feed from './page-views/Feed'
import ContentDetails from './page-views/ContentDetails'
import WatchHistory from './page-views/WatchedHistory';

function App() {

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/feed" element={<Feed />} />
        <Route path="/content/:id" element={<ContentDetails />} />
        <Route path="/watchHistory" element={<WatchHistory />} />
        <Route path="/*" element={<h1>Not Found</h1>} />
      </Routes>
    </div>
  );
}

export default App;
