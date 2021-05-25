import "./App.css";
import { TeamSummaryPage } from "./pages/TeamSummaryPage";
import { MatchPage } from "./pages/MatchPage";
import { TeamsPage } from "./pages/TeamsPage";
import { Nav } from "./components/Nav";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path={`/teams/:name/matches/:year/:against/:result`}>
            <Nav />
            <MatchPage />
          </Route>
          <Route path={`/teams/:name/:size`}>
            <Nav />
            <TeamSummaryPage />
          </Route>
          <Route path="/">
            <TeamsPage />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
