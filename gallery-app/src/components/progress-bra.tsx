import { useEffect, useState } from "react";

export default function ProgressBar({ timer }: { timer: number }) {
  const [reamainingTime, setRemainingTime] = useState<number>(timer);

  useEffect(() => {
    const interval = setInterval(() => {
      console.log("interval");
      setRemainingTime(prevTime => prevTime - 10);
    }, 10);
    return () => {
      clearInterval(interval);
    }
  }, [reamainingTime]);

  return <progress
    className="w-full mt-4 h-2 rounded-full bg-gray-200 [&::-webkit-progress-bar]:bg-gray-200 [&::-webkit-progress-value]:bg-blue-600 [&::-moz-progress-bar]:bg-blue-600"
    value={reamainingTime}
    max={timer}
  />
}