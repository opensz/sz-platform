/*
* last update: 2006-08-24
*/

editAreaLoader.load_syntax["html"] = {
	'DISPLAY_NAME' : 'HTML'
	,'COMMENT_SINGLE' : {}
	,'COMMENT_MULTI' : {'<!--' : '-->'}
	,'QUOTEMARKS' : {1: "'", 2: '"'}
	,'KEYWORD_CASE_SENSITIVE' : false
	,'KEYWORDS' : {
		'keywords' : ['include','channel','singlearticle','arclist','flash','colnav','custom','column',
		              'colarclist','global','rss','rsscol','linkgroup','vote','showsitestat',
		              'addsitestat','list','pagenav','curpos','singlearticle','addcomlumstat',
		              'feedback','imglist','software','showarchivestat','addarchivestat','relate'
		              ]
	}
	,'OPERATORS' :[
	]
	,'DELIMITERS' :[
	]
	,'REGEXPS' : {
		
		'doctype' : {
			'search' : '()(<!DOCTYPE[^>]*>)()'
			,'class' : 'doctype'
			,'modifiers' : ''
			,'execute' : 'before' // before or after
		}
		
		,'tags' : {
			'search' : '(<ht:)(/?[a-z][^ \r\n\t>]*)([^>]*>)|(</ht:(\.*>))'
			,'class' : 'tags'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
		
		
		,'htmltags' : {
			'search' : '(<)(/?[a-z][^ \r\n\t>]*)([^>]*>)'
			,'class' : 'htmltags'
			,'modifiers' : 'gi'
			,'execute' : 'before' // before or after
		}
		
		,'attributes' : {
			'search' : '( |\n|\r|\t)([^ \r\n\t=]+)(=)'
			,'class' : 'attributes'
			,'modifiers' : 'g'
			,'execute' : 'before' // before or after
		}
		
	}
	,'STYLES' : {
		'COMMENTS': 'color: #AAAAAA;'
		,'QUOTESMARKS': 'color: #6381F8;'
		
		,'OPERATORS' : 'color: #E775F0;'
		,'DELIMITERS' : ''
		,'REGEXPS' : {
		
			'attributes': 'color: #B1AC41;',
			'htmltags':'color: #3F7F7F;',
			'tags': 'color: #ff0000;',
			'endtags': 'color: #ff0000;',
			'doctype': 'color: #8DCFB5;'
		}	
		,'KEYWORDS' : {
		
		}
	}		
};
