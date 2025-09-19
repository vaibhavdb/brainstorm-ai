import React from "react";
import Dashboard from "./components/Dashboard";

export default function App(){
  return (
    <div style={{maxWidth: 1000, margin: "24px auto", fontFamily: "Inter, Arial"}}>
      <h1>CodeScribe Dashboard</h1>
      <Dashboard />
    </div>
  );
}
