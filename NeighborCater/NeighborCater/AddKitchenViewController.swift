//
//  AddKitchenViewController.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import UIKit

class AddKitchenViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var location: UITextField!
    var longitude : Double = 0.0
    var latitude : Double = 0.0
    
    let gpaViewController = GooglePlacesAutocomplete(
        apiKey: "AIzaSyBbdYNbqJZm0gWCjBT7wY3iG7fOigkOTNc",
        placeType: .Address
    )
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        gpaViewController.placeDelegate = self
        
    }
    
    func textFieldDidBeginEditing(textField: UITextField) {
        if textField == location {
            presentViewController(gpaViewController, animated: true, completion: nil)
            
        }
    }
    
    @IBAction func complete(sender: AnyObject) {
        
    }
    
    
    
}

extension AddKitchenViewController: GooglePlacesAutocompleteDelegate {
    func placeSelected(place: Place) {
        place.getDetails { details in
            print(details)
            self.latitude = details.latitude
            self.longitude = details.longitude
            self.location.text = place.description
        }
        placeViewClosed()
    }
    
    func placeViewClosed() {
        dismissViewControllerAnimated(true, completion: nil)
    }
}
