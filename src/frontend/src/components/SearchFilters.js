import { React, useEffect, useState } from "react";
import { useParams, useHistory } from "react-router-dom";

export const SearchFilters = ({ yearParam, againstParam, resultParam }) => {
  const history = useHistory();
  const { name, year, against, result } = useParams();
  const startYear = process.env.REACT_APP_DATA_YEAR_START;
  const endYear = process.env.REACT_APP_DATA_YEAR_END;
  const base_path = `/teams/${name}/matches/`;

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

  var years = [];
  for (let i = startYear; i <= endYear; i++) {
    years.push(i);
  }
  return (
    <>
      <div className="SearchFilters" id="year">
        {/* <h2>Year Selection</h2> */}
        <label htmlFor="year"> Year Selection:</label>
        <br />
        <select
          name="year"
          id="year"
          onChange={e => {
            history.push(`${base_path}${e.target.value}/${against}/${result}`);
          }}
        >
          {years.map(year => {
            return (
              <option key={year} value={year}>
                {year}
              </option>
            );
          })}
        </select>
      </div>
      <div className="SearchFilters" id="team">
        <label htmlFor="team">Played Against Team:</label>
        <br />
        <select
          name="team"
          id="team"
          onChange={e => {
            history.push(`${base_path}${year}/${e.target.value}/${result}`);
          }}
        >
          {teams.length > 0 &&
            teams.map(team =>
              team.name !== name ? (
                <option key={team.id} value={team.name}>
                  {team.name}
                </option>
              ) : (
                ""
              )
            )}
        </select>
      </div>
      <div className="SearchFilters" id="result">
        <label htmlFor="result">Match Result:</label>
        <br />
        <select
          name="result"
          id="result"
          onChange={e => {
            history.push(`${base_path}${year}/${against}/${e.target.value}`);
          }}
        >
          <option key="all" value="all">
            All
          </option>
          <option key="won" value="won">
            Won
          </option>
          <option key="lost" value="lost">
            Lost
          </option>
        </select>
      </div>
    </>
  );
};
