import { React, useEffect, useState } from "react";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { Link, useParams } from "react-router-dom";
import "../pages/MatchPage.css";
import { SearchFilters } from "../components/SearchFilters";

export const MatchPage = () => {
  const { name, year, against, result } = useParams();
  const [teamMatches, setTeamMatches] = useState({});
  const size = 4;

  useEffect(() => {
    const fetchTeamMatches = async () => {
      const response = await fetch(
        process.env.REACT_APP_DATA_API_URL +
          `/teams/${name}/matches?year=${year}&against=${against}&result=${result}`
      );
      const data = await response.json();
      console.log(data);
      setTeamMatches(data);
    };

    fetchTeamMatches();
  }, [name, year, against, result]);
  var teams;
  if (teamMatches.length > 0) {
    teams = (
      <div className="small-cards">
        {teamMatches.map(match => (
          <MatchSmallCard match={match} key={match.id} />
        ))}
      </div>
    );
  } else {
    teams = (
      <div className="small-cards no-result">No matching results found!</div>
    );
  }

  return (
    <div className="MatchPage">
      <div className="teams-header">
        <Link to={`/teams/${name}/${size}`}>
          {" "}
          <h1>{name}</h1>
        </Link>
      </div>
      <SearchFilters
        yearParam={year}
        againstParam={against}
        resultParam={result}
      />
      {teams}
    </div>
  );
};
