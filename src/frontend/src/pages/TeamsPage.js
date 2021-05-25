import { React, useEffect, useState } from "react";
import { TeamCard } from "../components/TeamCard";
import "../pages/TeamsPage.css";

export const TeamsPage = () => {
  const [teams, setTeams] = useState({});
  useEffect(() => {
    const fetchTeams = async () => {
      const response = await fetch(
        process.env.REACT_APP_DATA_API_URL + "/teams"
      );
      const data = await response.json();
      console.log(data);
      setTeams(data);
    };

    fetchTeams();
  }, []);

  if (teams === undefined) {
    return "No data available.";
  } else {
    return (
      <>
        <h1 className="teams-header">IPL Teams Dashboard </h1>
        <div className="TeamsPage">
          {teams.length > 0 &&
            teams.map(team => <TeamCard team={team} key={team.id} />)}
        </div>
      </>
    );
  }
};
