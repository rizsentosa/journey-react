import { useEffect, useState } from "react";

type Props = {
  timeout: number,
  mode: string,
  onTimeout: (() => void) | null
}
export default function QuestionTimer({ timeout, onTimeout, mode }: Props) {
  const [remainingTime, setRemainingTime] = useState<number>(timeout);

  useEffect(() => {
    const timer = setTimeout(onTimeout!, timeout);
    return () => clearTimeout(timer);
  }, [onTimeout, timeout]);

  useEffect(() => {
    const interval = setInterval(() => {
      setRemainingTime(prevRemainingTime => prevRemainingTime - 100);
    }, 100);

    return () => clearInterval(interval);
  }, []);

  return <progress id="question-time" max={timeout} value={remainingTime} className={mode} />
}