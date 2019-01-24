var polishSpecChars = 'ĄĆĘŁŃÓŚŻŹąćęłńóśżź'
var specSEP = "@"

function charToSpec(ch) {
	console.log("charToSpec: "+ch)
    for(var i=0; i < polishSpecChars.length; i++ ) 
    {
    	var c = polishSpecChars.substring(i,i+1)
//    	console.log("char("+i+")="+c)
        if (ch == c) {
//        	console.log("charToSpec-result: "+specSEP+i+specSEP)
            return specSEP+i+specSEP
        }
    }
	console.log("charToSpec-result: "+ch)
    return ch
}

function codePolishWordToWordWithSpecs(word) {
	console.log("codePolishWordToWordWithSpecs: "+word)
    var result = ""
    for(var i=0; i < word.length; i++ ) {
        result += charToSpec(word.charAt(i))
    }
	console.log("codePolishWordToWordWithSpecs-result: "+result)
    return result
}


function decodeWordWithSpecsToPolishWord(word) {
	console.log("decodeWordWithSpecsToPolishWord: "+word)	
    for(var i=0; i < polishSpecChars.length; i++ ) 
    {
        word = word.replace(specSEP+i+specSEP, polishSpecChars.charAt(i))        
    }
	console.log("decodeWordWithSpecsToPolishWord-result: "+word)
    return word
}
