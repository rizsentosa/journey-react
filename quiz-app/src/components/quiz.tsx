import { useCallback, useState } from "react"
import { Questions } from "../types/question.type";
import Question from "./question";
import Summary from "./summary";


export default function Quiz() {
  const [userAnswers, setUserAnswers] = useState<string[]>([]);
  const activeQuestionIndex = userAnswers.length;
  const quizCompleted = activeQuestionIndex === Questions.length;

  const handleSelectedAnswer = useCallback((selectedAnswer: string | null) => {
    setUserAnswers((prevAnswer) => {
      return [...prevAnswer, selectedAnswer!];
    });
  }, []);

  const handleSkipAnswer = useCallback(() => {
    handleSelectedAnswer(null)
  }, [handleSelectedAnswer]);

  if (quizCompleted) {
    return <>
      <Summary userAnswers={userAnswers} />
    </>
  }
  return <div id="quiz">
    <Question
      key={activeQuestionIndex}
      index={activeQuestionIndex}
      onSelectedAnswer={handleSelectedAnswer}
      onSkipAnswer={handleSkipAnswer}
    />
  </div>
}