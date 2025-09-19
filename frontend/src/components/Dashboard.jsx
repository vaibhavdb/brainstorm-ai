import React, { useState, useEffect, useRef } from "react";
import ProjectView from "./ProjectView";

export default function Dashboard() {
  const [repoUrl, setRepoUrl] = useState("");
  const [jobId, setJobId] = useState(null);
  const [jobStatus, setJobStatus] = useState(null);
  const [result, setResult] = useState(null);
  const pollRef = useRef(null);

  const startJob = async () => {
    setResult(null);
    setJobStatus(null);

    try {
      const resp = await fetch("http://localhost:8080/api/v1/projects/jobs", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ repoUrl }),
      });
      const data = await resp.json();
      if (data.jobId) {
        setJobId(data.jobId);
      } else {
        alert("Failed to start job: " + JSON.stringify(data));
      }
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  useEffect(() => {
    if (!jobId) return;

    pollRef.current = setInterval(async () => {
      try {
        const r = await fetch(`http://localhost:8080/api/v1/projects/jobs/${jobId}`);
        if (!r.ok) return;
        const s = await r.json();
        setJobStatus(s);
        if (s.status === "DONE" || s.status === "FAILED") {
          clearInterval(pollRef.current);
          if (s.result) {
            setResult(s.result);
          } else {
            alert("Job failed: " + s.progressNote);
          }
        }
      } catch (e) {
        console.error(e);
      }
    }, 2000);

    return () => clearInterval(pollRef.current);
  }, [jobId]);

  return (
    <div>
      <div style={{ display: "flex", gap: 12 }}>
        <input
          style={{ flex: 1, padding: 8 }}
          placeholder="https://github.com/owner/repo"
          value={repoUrl}
          onChange={(e) => setRepoUrl(e.target.value)}
        />
        <button onClick={startJob}>Start Summarize Job</button>
      </div>

      <div style={{ marginTop: 16 }}>
        {jobStatus && (
          <div style={{ padding: 12, border: "1px solid #eee", borderRadius: 6 }}>
            <strong>Job:</strong> {jobStatus.jobId} — <em>{jobStatus.status}</em>
            <div>Progress: {jobStatus.progress ?? 0}%</div>
            <div style={{ color: "#666" }}>{jobStatus.progressNote}</div>
          </div>
        )}
      </div>

      <div style={{ marginTop: 24 }}>
        {result ? <ProjectView data={result} /> : <div style={{ color: "#888" }}>No result yet</div>}
      </div>
    </div>
  );
}
