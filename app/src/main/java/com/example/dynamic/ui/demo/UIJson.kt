package com.example.dynamic.ui.demo

val uiList = listOf(ROOT_JSON, ITEM_JSON)

const val ROOT_JSON = """
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
                    "height": -2,
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
                    "height": -2,
                    "width": -2
                },
                "children": [
                    {
                        "componentName": "IconButton",
                        "layout": {
                            "height": -2,
                            "width": -2
                        },
                        "value": {
                            "image": "Menu"
                        }
                    },
                    {
                        "componentName": "IconButton",
                        "layout": {
                            "height": -2,
                            "width": -2
                        },
                        "value": {
                            "image": "Add"
                        }
                    },
                    {
                        "componentName": "IconButton",
                        "layout": {
                            "height": -2,
                            "width": -2
                        },
                        "value": {
                            "image": "Close"
                        }
                    }
                ]
            },
            {
                "componentName": "SnackBar"
            },
            {
                "componentName": "FloatingActionButton",
                "layout": {
                    "height": -2,
                    "width": -2
                },
                "children": [
                    {
                        "componentName": "IconButton",
                        "value": {
                            "image": "Add"
                        }
                    }
                ]
            },
            {
               "componentName": "LazyColumn",
               "layout": {
                   "height": -1,
                   "width": -1
               },
               "children": [
                    {
                        "componentName": "Other",
                        "componentType": "item"
                    }
               ]
            }
        ]
    }
"""

const val ITEM_JSON = """
    {
        "componentType": "item",
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
            "backgroundColor": "#FFEFB8C8",
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
                    "width": 168,
                    "start": 16,
                    "end": 16,
                    "top": 16,
                    "bottom": 16
                },
                "style": {
                    "fontSize": 16,
                    "fontColor": "#FFFFFF",
                    "align": "TopCenter",
                    "backgroundColor": "#80666666",
                    "topLeft": 16,
                    "topRight": 16,
                    "bottomLeft": 16,
                    "bottomRight": 16
                },
                "value": {
                    "click": [
                        {
                            "content": "I am a SnackBar.",
                            "type": "SnackBar",
                            "tryFirst": true
                        }
                    ]
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
