import React from "react";
import { Link, useParams } from "react-router-dom";

export const MatchSmallCard = ({ match }) => {
  const size = 4;
  const { name } = useParams();
  const matchResult = match.winner !== name ? "lost" : "won";
  const otherTeam = match.team1 !== name ? match.team1 : match.team2;
  return (
    <div className={`MatchSmallCard ${matchResult}`}>
      <p>
        Vs <br />
        <b className="other-team">
          <Link to={`/teams/${otherTeam}/${size}`}>{otherTeam}</Link>
        </b>{" "}
        at <b>{match.city}</b> on <b>{match.date}</b>
      </p>
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
  );
};
