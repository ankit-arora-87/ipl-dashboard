import React from "react";
import { Link } from "react-router-dom";

export const TeamCard = ({ team }) => {
  const size = 4;
  return (
    <div className="TeamCard">
      <h2>
        <Link to={`/teams/${team.name}/${size}`}>{team.name}</Link>
      </h2>
      <p>Won/ Played</p>
      <p>
        {team.totalWins}/ {team.totalMatches}
      </p>
    </div>
  );
};
