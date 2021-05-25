import { React, useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";
import { PieChart } from "react-minimal-pie-chart";
import "../components/MatchDetailCard.css";
import "../components/MatchSmallCard.css";
import "../pages/TeamSummaryPage.css";

export const TeamSummaryPage = () => {
  const { name } = useParams();
  const year = 2020;
  const size = 4;
  const [team, setTeam] = useState({ latestMatches: [] });

  useEffect(() => {
    const fetchMatches = async () => {
      const response = await fetch(
        process.env.REACT_APP_DATA_API_URL + `/teams/${name}/${size}`
      );
      const data = await response.json();
      console.log(data);
      setTeam(data);
    };

    fetchMatches();
  }, [name]);

  if (team.latestMatches.length > 0) {
    return (
      <>
        <div className="TeamSummaryPage">
          <div className="team-header">
            {" "}
            <h1>
              <Link to={`/teams/${name}/matches/${year}/all/all`}>
                {team.name}
              </Link>
            </h1>
          </div>
          <MatchDetailCard
            match={team.latestMatches[0]}
            key={team.latestMatches[0].id}
          />
          <div className="pie-chart">
            <PieChart
              data={[
                {
                  title:
                    "Lost - " +
                    (team.totalMatches - team.totalWins) +
                    " matches",
                  value: team.totalMatches - team.totalWins,
                  color: "#a93d55"
                },
                {
                  title: "Won - " + team.totalWins + " matches",
                  value: team.totalWins,
                  color: "green"
                }
              ]}
            />
          </div>
          {team.latestMatches.slice(1).map(match => (
            <MatchSmallCard match={match} key={match.id} />
          ))}
          <div className="more">
            <Link to={`/teams/${name}/matches/${year}/all/all`}>More >></Link>
          </div>
          {/* </div> */}
        </div>
      </>
    );
  } else return null;
};
