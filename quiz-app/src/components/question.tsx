import { useState } from "react";
import Answers from "./answers";
import QuestionTimer from "./question-timer";
import { Questions } from "../types/question.type";

type Props = {
  index: number
  onSelectedAnswer: (selectedAnswer: string | null) => void
  onSkipAnswer: () => void
}

export default function Question({ index, onSelectedAnswer, onSkipAnswer }: Props) {
  const [answer, setAnswer] =
    useState<{ selectedAnswer: string, isCorrect: boolean | null }>({
      selectedAnswer: "",
      isCorrect: null
    });

  let timer = 10000;
  if (answer.selectedAnswer && answer.selectedAnswer) {
    timer = 1000;
  }

  if (answer.isCorrect !== null) {
    timer = 2000;
  }

  const handleSelectAnswer = (answer: string | null) => {
    setAnswer({
      selectedAnswer: answer!,
      isCorrect: null
    });

    setTimeout(() => {
      setAnswer({
        selectedAnswer: answer!,
        isCorrect: Questions[index].answers[0] === answer
      });

      setTimeout(() => {
        onSelectedAnswer(answer);
      }, 2000);

    }, 1000);
  }

  let answerState = "";
  if (answer.selectedAnswer && answer.isCorrect !== null) {
    answerState = answer.isCorrect ? "correct" : "wrong";
  } else if (answer.selectedAnswer) {
    answerState = "answered";
  }

  return (
    <div id="question">
      <QuestionTimer
        key={timer}
        timeout={timer}
        mode={answerState}
        onTimeout={answer.selectedAnswer === "" ? onSkipAnswer : null} />

      <h2>{Questions[index].text}</h2>

      <Answers
        questionIndex={index}
        answerState={answerState}
        selectedAnswer={answer.selectedAnswer!}
        onSelect={handleSelectAnswer} />
    </div>
  )
}