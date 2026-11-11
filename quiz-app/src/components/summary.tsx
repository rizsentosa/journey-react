import summaryImg from "../assets/quiz-complete.png";
import { Questions } from "../types/question.type";
type Props = {
  userAnswers: string[]
}

export default function Summary({ userAnswers }: Props) {
  const skippedAnswer = userAnswers.filter(answer => answer === null);
  const correctAnswer = userAnswers.filter((answer, index) => answer === Questions[index].answers[0]);
  const skippedAnswersShare = Math.round((skippedAnswer.length / userAnswers.length) * 100);
  const correctAnswersShare = Math.round((correctAnswer.length / userAnswers.length) * 100);
  const wrongAnswersShare = 100 - skippedAnswersShare - correctAnswersShare;
  return (
    <div id="summary">
      <img src={summaryImg} alt="summary image" />
      <h2>quiz completed</h2>
      <div id="summary-stats">
        <p>
          <span className="number">{skippedAnswersShare}%</span>
          <span className="text">skipped</span>
        </p>
        <p>
          <span className="number">{correctAnswersShare}%</span>
          <span className="text">answer correctly</span>
        </p>
        <p>
          <span className="number">{wrongAnswersShare}%</span>
          <span className="text">answer incorrectly</span>
        </p>
      </div>
      <ol>
        {userAnswers.map((answer, index) => {
          let cssClass = "user-answer";
          if (answer === null) {
            cssClass += " skipped";
          } else if (answer === Questions[index].answers[0]) {
            cssClass += " correct"
          } else {
            cssClass += " wrong"
          }
          return <li key={`${index}_${answer}`}>
            <h3>{index + 1}</h3>
            <p className="question">{Questions[index].text}</p>
            <p className={cssClass}>{answer ?? "skipped"}</p>
          </li>
        })}
      </ol>
    </div>
  )
}