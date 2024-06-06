package jjh.preinterview.ui.home

data class HomeUiState(
  val questionTitles: List<Question>,
  val selectedQuestion: Question,
  val questionContentList: Map<Question, List<String>>,
  val occupationalGroup: List<Occupational>,
  val selectedOccupational: Occupational,
) {
  companion object {
    fun init(): HomeUiState {
      return HomeUiState(
        questionTitles = Question.entries,
        selectedQuestion = Question.BASICS,
        questionContentList = questionContentList(),
        occupationalGroup = Occupational.entries,
        selectedOccupational = Occupational.DEVELOPER,
      )
    }
  }
}

enum class Question(val string: String) {
  BASICS("기본"), EXPERIENCE("경험"), VALUES("가치관"), SITUATION("상황")
}

enum class Occupational(val string: String) {
  DEVELOPER("개발 및 데이터")
}

private fun questionContentList(): Map<Question, List<String>> {
  return mapOf(
    Question.BASICS to listOf("간단한 자기소개를 해 주세요", "본인의 성격을 한마디로 말해주세요"),

    Question.EXPERIENCE to listOf("실행했던 프로젝트 중 가장 기억에 남는 것을 말해주세요"),

    Question.VALUES to listOf("상사가 불법적인 지시를 내린다면 어떻게 하시겠습니까?"),

    Question.SITUATION to listOf("친한 친구 2명과 여행을 갔습니다. 한 친구는 계획적으로 움직이자 제안하고 한 친구는 무계획으로 움직이자고 합니다. 당신은 어떻게 하시겠습니까?"),
  )
}