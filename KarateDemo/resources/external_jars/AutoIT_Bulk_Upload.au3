#Region ;**** Directives created by AutoIt3Wrapper_GUI ****
#AutoIt3Wrapper_UseX64=y
#EndRegion ;**** Directives created by AutoIt3Wrapper_GUI ****
WinWait("[CLASS:#32770]", "", 15)
Sleep(5000)
ControlFocus("Open", "", "Edit1")
ControlSetText("Open","", "Edit1", $CmdLine[1])
Sleep(5000)
ControlFocus("Open", "", "Button1")
ControlClick("Open", "", "Button1")
Sleep(2000)
ControlFocus("Open", "", "Edit1")
ControlSetText("Open","", "Edit1", $CmdLine[2])
Sleep(2000)
ControlFocus("Open", "", "Button1")
ControlClick("Open", "", "Button1")