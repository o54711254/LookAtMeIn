import React from "react";
// import { Link } from "react-router-dom";
import { useState } from "react";
import HospitalRegistForm from "./HospitalRegistForm";
import UserRegistForm from "./UserRegistForm";

// import { BrowserRouter, Routes, Route } from "react-router-dom";

function Regist() {
  return (
    <div>
      <HospitalRegistForm />
      <UserRegistForm />
    </div>
  );
}
export default Regist;
