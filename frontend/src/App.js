
import React, { useState } from "react";
import "./App.css";
import { motion } from "framer-motion";
import { PieChart, Pie, Cell } from "recharts";
import { useDropzone } from "react-dropzone";

function App() {
  const [role, setRole] = useState("");
  const [skills, setSkills] = useState("");
  const [jobSkills, setJobSkills] = useState("");
  const [suggestion, setSuggestion] = useState("");
  const [match, setMatch] = useState(0);
  const [missingSkills, setMissingSkills] = useState([]);
  const BASE_URL = "https://resume-ai-2-64ih.onrender.com/api";
   const [file, setFile] = useState(null);

    // Drag & Drop
    const onDrop = (acceptedFiles) => {
      setFile(acceptedFiles[0]);
    };

    const { getRootProps, getInputProps } = useDropzone({
      onDrop,
      accept: {
        "application/pdf": [],
        "application/msword": [],
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document": [],
      },
    });

    const uploadResume = async () => {
      const formData = new FormData();
      formData.append("file", file);

      await fetch(${BASE_URL}/upload, {
        method: "POST",
        body: formData,
      });

      alert("Resume uploaded!");
    };
  const getSuggestion = async () => {
    const res = await fetch(${BASE_URL}/suggest, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ role, skills, jobSkills }),
    });

    const text = await res.text();
    setSuggestion(text);

    // Extract missing skills from response (simple parsing)
    const match = text.match(/Skills: (.*)/);
    if (match) {
      setMissingSkills(match[1].split(",").map(s => s.trim()));
    }
  };

  const getMatch = async () => {
  const res = await fetch(
    `${BASE_URL}/match?resumeSkills=${skills}&jobSkills=${jobSkills}`,
    { method: "POST" }
  );

  const data = await res.text();
  setMatch(parseInt(data));
};

  const chartData = [
    { name: "Match", value: match },
    { name: "Gap", value: 100 - match },
  ];

  return (
    <div className="page">
      <div className="navbar">AI Resume Builder</div>

      <div className="main">
        {/* LEFT PANEL */}
       <div className="left">
         <h2>Profile</h2>

         {/* ✅ ADD HERE */}
         <div {...getRootProps()} className="dropzone">
           <input {...getInputProps()} />
           {file ? (
             <p>📄 {file.name}</p>
           ) : (
             <p>Drag & drop resume here OR click</p>
           )}
         </div>

         {file && (
           <button onClick={uploadResume}>Upload Resume</button>
         )}

         <input placeholder="Role" onChange={e => setRole(e.target.value)} />
         <textarea placeholder="Your Skills" onChange={e => setSkills(e.target.value)} />
         <textarea placeholder="Job Skills" onChange={e => setJobSkills(e.target.value)} />

         <button onClick={getSuggestion}>Analyze Resume</button>
         <button onClick={getMatch}>Check Match</button>
       </div>

        {/* RIGHT PANEL */}
        <div className="right">
          <motion.div className="card" initial={{ opacity: 0 }} animate={{ opacity: 1 }}>

            <h3>📊 Match Score</h3>

            <PieChart width={200} height={200}>
              <Pie
                data={chartData}
                innerRadius={50}
                outerRadius={80}
                dataKey="value"
              >
                <Cell fill="#0073b1" />
                <Cell fill="#e0e0e0" />
              </Pie>
            </PieChart>

            <p className="score">{match}%</p>
          </motion.div>

          <div className="card">
            <h3>💡 Suggestions</h3>
            <pre>{suggestion}</pre>
          </div>

          <div className="card">
            <h3>❌ Missing Skills</h3>

            <div className="tags">
              {missingSkills.map((skill, i) => (
                <span key={i} className="tag">{skill}</span>
              ))}
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}

export default App;
