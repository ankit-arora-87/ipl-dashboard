import React from "react";
import { Link, useParams } from "react-router-dom";

export const MatchDetailCard = ({ match }) => {
  const size = 4;
  const { name } = useParams();
  const matchResult = match.winner !== name ? "lost" : "won";
  const otherTeam = match.team1 !== name ? match.team1 : match.team2;

  return (
    <div className={`MatchDetailCard ${matchResult}`}>
      <div>
        Vs <br />
        <b className="other-team">
          {" "}
          <Link to={`/teams/${otherTeam}/${size}`}>{otherTeam}</Link>
        </b>{" "}
        <br />
        at <b>{match.city}</b> on <b>{match.date}</b>
        <p>
          <b>
            Match Winner: &nbsp;
            {match.winner}
          </b>{" "}
          won by{" "}
          <b>
            {match.resultMargin} {match.result}
          </b>
        </p>
        <p>
          <b>Player Of Match:</b> {match.playerOfMatch}
        </p>
      </div>

      <div>
        <br />
        <p>
          <b>First Innings Team:</b> {match.team1}
        </p>
        <p>
          <b>Second Innings Team:</b> {match.team2}
        </p>
        <p>
          <b>Match Umpires:</b> {match.umpire1}, {match.umpire2}
        </p>
      </div>
    </div>
  );
};
