import { Questions } from "../types/question.type";

type Props = {
  questionIndex: number,
  answerState: string,
  selectedAnswer: string,
  onSelect: (answer: string | null) => void
}

const shuffledQuestions = Questions.map(q => ({
  ...q,
  answers: [...q.answers].sort(() => Math.random() - 0.5)
}));

export default function Answers({ questionIndex, answerState, selectedAnswer, onSelect }: Props) {
  return (
    <ul id="answers">
      {
        shuffledQuestions[questionIndex].answers.map(answer => {
          let cssClass = "";
          const isSelected = selectedAnswer === answer;
          if (answerState === "answered" && isSelected) {
            cssClass = "selected";
          }
          if (answerState === "correct" && isSelected) {
            cssClass = "correct";
          }
          if (answerState === "wrong" && isSelected) {
            cssClass = "wrong";
          }
          return (
            <li key={answer} className="answer">
              <button onClick={() => onSelect(answer)} className={cssClass} disabled={answerState !== ""}>{answer}</button>
            </li>
          )
        })
      }
    </ul>
  )
}