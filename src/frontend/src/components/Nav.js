import React from "react";
import { Link } from "react-router-dom";
import "./Nav.css";

export const Nav = () => {
  return (
    <div className="NavLink">
      <Link to={`/teams`}> Home</Link>
    </div>
  );
};
