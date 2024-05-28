package jjh.study.audiorecording.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jjh.study.audiorecording.R
import jjh.study.audiorecording.util.Spacer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
) {

  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    36.Spacer()
    Image(painter = painterResource(id = R.drawable.main_logo), contentDescription = "MainLogo")
    72.Spacer()
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = stringResource(id = R.string.interview_question),
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_bold)),
          fontWeight = FontWeight.W700,
          color = Color(0xff252E33),
          lineHeight = 24.04.sp
        )
      )

    }

    //font-family: Spoqa Han Sans Neo;
    //font-size: 20px;!
    //font-weight: 700;
    //line-height: 25.04px;
    //letter-spacing: -0.05em;
    //text-align: left;

    // TODO: 드롭박스 만드는거임
//    ExposedDropdownMenuBox(
//      expanded = true,
//      onExpandedChange = {
//
//      },
//    ) {
//      TextField(
//        readOnly = true,
//        value = "selectedOptionText",
//        onValueChange = { },
//        label = { Text("Categories") },
//        trailingIcon = {
//          ExposedDropdownMenuDefaults.TrailingIcon(
//            expanded = true
//          )
//        },
//        colors = ExposedDropdownMenuDefaults.textFieldColors()
//      )
//      ExposedDropdownMenu(
//        expanded = true,
//        onDismissRequest = {
////          expanded = false
//        }
//      ) {
//        listOf<String>().forEach { selectionOption ->
//          DropdownMenuItem(
//            onClick = {},
//            text = { Text(text = selectionOption) }
//          )
//
//        }
//      }
//    }
  }

}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
  HomeScreen(

  )
}