import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RegisterPage from './Page/RegisterPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/register" element={<RegisterPage />} />
        {/* Add more routes here as needed */}
      </Routes>
    </Router>
  );
}

export default App;
