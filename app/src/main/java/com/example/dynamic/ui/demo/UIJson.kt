package com.example.myapplication.ui.dynamic

val uiList = listOf(SCAFFOLD_JSON, ROOT_JSON, ITEM_JSON)

const val SCAFFOLD_JSON = """
    {
        "componentType": "Scaffold",
        "componentId": "Scaffold",
        "componentName": "Scaffold",
        "layout": {
            "width": -1,
            "height": -1
        },
        "children": [
            {
                "componentName": "TopBar",
                "layout": {
                    "height": 68,
                    "width": -1
                },
                "value": {
                    "text": "Dynamic UI"
                },
                "style": {
                    "fontSize": 24,
                    "fontColor": "#000000"
                }
            },
{
    "componentName": "BottomBar",
    "layout": {
    "height": 68,
    "width": -1
}
},
{
    "componentName": "SnackBar",
    "layout": {
    "height": 68,
    "width": -1
}
},
{
    "componentName": "FloatingActionButton",
    "layout": {
        "height": 68,
        "width": 68
    },
    "style": {
        "backgroundColor": "#228811"
    },
        "children": [
            {
                "componentName": "Text",
                "style": {
                    "fontSize": 48,
                    "fontColor": "#FF8811"
                },
        "value": {
            "text": "+"
        }
            }
        ]
},
            {
               "componentName": "Other",
               "componentType": "LazyColumn",
               "layout": {
                   "height": -1,
                   "width": -1
               }
            }
        ]
    }
"""

const val ROOT_JSON = """
    {
        "componentType": "LazyColumn",
        "componentId": "LazyColumn",
        "componentName": "LazyColumn",
        "layout": {
            "height": -1,
            "width": -1,
            "top": 4,
            "bottom": 4
        },
        "children": [
            {
                "componentName": "Other",
                "componentType": "99"
            }
        ]
    }

"""

const val ITEM_JSON = """
    {
        "componentType": "99",
        "componentId": "item",
        "componentName": "Row",
        "layout": {
            "width": -1,
            "height": 160,
            "start": 8,
            "end": 8,
            "top": 4,
            "bottom": 4
        },
        "style": {
            "backgroundColor": "#996633",
            "topLeft": 16,
            "topRight": 16,
            "bottomLeft": 16,
            "bottomRight": 16
        },
        "children": [
            {
                "componentId": "itemButton",
                "componentName": "Button",
                "layout": {
                    "height":68,
                    "width": 200,
                    "start": 16,
                    "end": 16,
                    "top": 16,
                    "bottom": 16
                },
                "style": {
                    "fontSize": 16,
                    "fontColor": "#FFFFFF",
                    "align": "TopCenter",
                    "backgroundColor": "#0066FF",
                    "topLeft": 34,
                    "topRight": 34,
                    "bottomLeft": 34,
                    "bottomRight": 34
                }
            },
            {
                "componentId": "itemImage",
                "componentName": "Image",
                "layout": {
                    "start": 16,
                    "end": 16,
                    "top": 16,
                    "bottom": 16
                },
                "style": {
                    "topLeft": 16,
                    "topRight": 16,
                    "bottomLeft": 16,
                    "bottomRight": 16
                }
            }
        ]
    }
"""
